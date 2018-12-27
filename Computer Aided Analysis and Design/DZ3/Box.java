import java.util.*;
import java.util.stream.Collectors;

public class Box {
    private double threshold;
    private double alpha;
    private GoalFunction goalFunction;
    private double explicitLowerLimit;
    private double explicitUpperLimit;
    private List<LimitationFunction> g;

    public Box(double threshold, double alpha, GoalFunction goalFunction, double explicitLowerLimit, double explicitUpperLimit, List<LimitationFunction> g) {
        this.threshold = threshold;
        this.alpha = alpha;
        this.goalFunction = goalFunction;
        this.explicitLowerLimit = explicitLowerLimit;
        this.explicitUpperLimit = explicitUpperLimit;
        if (g == null) {
            this.g = new ArrayList<>();
        } else {
            this.g = g;
        }
    }

    public Box(GoalFunction goalFunction, double explicitLowerLimit, double explicitUpperLimit, List<LimitationFunction> g) {
        this(1E-6, 1.3, goalFunction, explicitLowerLimit, explicitUpperLimit, g);
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double start(Matrix startingPoint) throws MatrixSizeIncompatibleException, StartingPointException {
        System.out.println("Starting Box's procedure");
        System.out.println("Starting point:");
        System.out.println(startingPoint);
        boolean startingPointOK = pointOK(startingPoint);

        if (startingPointOK) {
            startingPointOK = checkExplicitLimits(startingPoint);
        }

        if (!startingPointOK) {
            throw new StartingPointException("Starting point not within limitations, stopping Box's procedure!");
        }

        Matrix Xc = new Matrix(startingPoint);
        int n = startingPoint.getN();
        Matrix[] simplexPoints = new Matrix[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            Random r = new Random();
            simplexPoints[i] = new Matrix(n, 1);
            for (int j = 0; j < n; j++) {
                double randomDouble = r.nextDouble();

                double element = explicitLowerLimit + randomDouble * (explicitUpperLimit - explicitLowerLimit);
                simplexPoints[i].setElementAt(element, j, 0);
            }
            boolean pointOK = pointOK(simplexPoints[i]);
            while (!pointOK) {
                simplexPoints[i] = Xc.add(simplexPoints[i]).multiplyWithScalar(0.5);
                pointOK = pointOK(simplexPoints[i]);
            }

            for (int j = 0; j < n; j++) {
                double sum = 0;
                int k;
                for (k = 0; k <= i; k++) {
                    sum += simplexPoints[k].getElementAt(j, 0);
                }
                sum /= k;
                Xc.setElementAt(sum, j, 0);
            }
        }

        double bestValue = goalFunction.getValueAt(Xc).getElementAt(0, 0);
        int numberOfIterationsWithoutImprovement = 0;

        while (true) {
            int h1=0, h2=0;
            Map<Integer, Double> valueMap = new HashMap<>();
            for (int i = 0; i < 2 * n; i++) {
                double val = goalFunction.getValueAt(simplexPoints[i]).getElementAt(0, 0);
                valueMap.put(i, val);
            }

            Map<Integer,Double> sorted =
                    valueMap.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            Set<Map.Entry<Integer,Double>> set = sorted.entrySet();
            int k=0;
            for(Map.Entry<Integer,Double> entry:set){
                if(k==0) h1=entry.getKey();
                if(k==1) h2=entry.getKey();
                if(k>1) break;
                k++;
            }

            Xc = new Matrix(n, 1);
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < 2 * n; j++) {
                    if (j == h1) continue;
                    sum += simplexPoints[j].getElementAt(i, 0);
                }
                sum /= (2*n-1);
                Xc.setElementAt(sum, i, 0);
            }


            double FXc = goalFunction.getValueAt(Xc).getElementAt(0, 0);
            Matrix Xr = Xc.multiplyWithScalar(1 + alpha).subtract(simplexPoints[h1].multiplyWithScalar(alpha));

            for (int i = 0; i < n; i++) {
                double xRi = Xr.getElementAt(i, 0);
                if (xRi < explicitLowerLimit)
                    xRi = explicitLowerLimit;
                else if (xRi > explicitUpperLimit)
                    xRi = explicitUpperLimit;
                Xr.setElementAt(xRi, i, 0);
            }

            boolean pointOK = pointOK(Xr);
            while (!pointOK) {
                Xr = Xc.add(Xr).multiplyWithScalar(0.5);
                pointOK = pointOK(Xr);
            }

            double FXr = goalFunction.getValueAt(Xr).getElementAt(0,0);
            double FXh2=goalFunction.getValueAt(simplexPoints[h2]).getElementAt(0,0);
            if(FXr>FXh2)
                Xr = Xc.add(Xr).multiplyWithScalar(0.5);
            simplexPoints[h1]=Xr;

            double currentValue = goalFunction.getValueAt(Xc).getElementAt(0, 0);
            if (currentValue < bestValue) {
                bestValue = currentValue;
                numberOfIterationsWithoutImprovement = 0;
            } else {
                numberOfIterationsWithoutImprovement++;
            }

            if(Matrix.calculateDifferenceWithE(simplexPoints[h1],Xc,null,threshold) || numberOfIterationsWithoutImprovement == 100) break;
        }

        System.out.println("Min for x=");
        System.out.println(Xc);
        double min = goalFunction.getValueAt(Xc).getElementAt(0, 0);
        System.out.println("Min = " + min);
        return min;
    }

    private boolean checkExplicitLimits(Matrix point) {
        for (int i = 0, n = point.getN(); i < n; i++) {
            double el = point.getElementAt(i, 0);
            if (el < explicitLowerLimit || el > explicitUpperLimit) return false;
        }
        return true;
    }

    private boolean pointOK(Matrix point) {
        for (LimitationFunction fun : g) {
            if (fun.getResult(point) < 0) {
                return false;
            }
        }
        return true;
    }
}
