import java.util.ArrayList;
import java.util.List;

public class TransformationWithoutLimitations {
    private double threshold;
    private GoalFunction goalFunction;
    private double explicitLowerLimit;
    private double explicitUpperLimit;
    private List<LimitationFunction> g;
    private List<LimitationFunction> h;
    private double t;

    public TransformationWithoutLimitations(double threshold, GoalFunction goalFunction, double explicitLowerLimit, double explicitUpperLimit, List<LimitationFunction> g, List<LimitationFunction> h, double t) {
        this.threshold = threshold;
        this.goalFunction = goalFunction;
        this.explicitLowerLimit = explicitLowerLimit;
        this.explicitUpperLimit = explicitUpperLimit;
        if (g == null) {
            this.g = new ArrayList<>();
        } else {
            this.g = g;
        }
        if (h == null) {
            this.h = new ArrayList<>();
        } else {
            this.h = h;
        }
        this.t = t;
    }

    public TransformationWithoutLimitations(GoalFunction goalFunction, double explicitLowerLimit, double explicitUpperLimit, List<LimitationFunction> g, List<LimitationFunction> h) {
        this(1E-6, goalFunction, explicitLowerLimit, explicitUpperLimit, g, h, 1);
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double start(Matrix startingPoint) throws MatrixSizeIncompatibleException {
        System.out.println("Starting transformation in problem without limitations");
        System.out.println("Starting point:");
        System.out.println(startingPoint);
        boolean startingPointOK = true;
        for (LimitationFunction fun : g) {
            if (fun.getResult(startingPoint) < 0) {
                startingPointOK = false;
                break;
            }
        }

        if (startingPointOK) {
            startingPointOK = checkExplicitLimits(startingPoint);
        }

        if (!startingPointOK) {
            System.out.println("Starting point not within limitations, searching for new starting point !");
            InsidePointFunction fun = new InsidePointFunction(g);
            SimplexNelderMead simplexNelderMead = new SimplexNelderMead(fun);
            startingPoint = simplexNelderMead.startForPoint(startingPoint);
            System.out.println("New starting point:");
            System.out.println(startingPoint);
        }

        double bestValue = goalFunction.getValueAt(startingPoint).getElementAt(0, 0);
        int numberOfIterationsWithoutImprovement = 0;
        Matrix oldX;
        Matrix currentX = new Matrix(startingPoint);
        while (true) {
            oldX = new Matrix(currentX);
            TransformedFunction transformedFunction = new TransformedFunction(goalFunction, g, h, t);

            SimplexNelderMead simplexNelderMead = new SimplexNelderMead(transformedFunction);
            currentX = new Matrix(simplexNelderMead.startForPoint(oldX));

            /*
            HookeJeeves hookeJeeves=new HookeJeeves(transformedFunction);
            currentX=new Matrix(hookeJeeves.startForPoint(oldX));
            */
            //System.out.println(currentX);

            double currentValue = goalFunction.getValueAt(currentX).getElementAt(0, 0);
            if (currentValue < bestValue) {
                bestValue = currentValue;
                numberOfIterationsWithoutImprovement = 0;
            } else {
                numberOfIterationsWithoutImprovement++;
            }
            if (Matrix.calculateDifferenceWithE(currentX, oldX, null, threshold) || numberOfIterationsWithoutImprovement == 100)
                break;

            t = t * 10;
        }

        System.out.println("Min for x=");
        System.out.println(currentX);
        double min = goalFunction.getValueAt(currentX).getElementAt(0, 0);
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
}

class InsidePointFunction implements GoalFunction {
    private List<LimitationFunction> g;

    public InsidePointFunction(List<LimitationFunction> g) {
        this.g = g;
    }

    @Override
    public Matrix getValueAt(Matrix point) {
        double result = 0;
        for (LimitationFunction fun : g) {
            double x = fun.getResult(point);
            if (x > 0) continue;
            result = result - x;
        }
        Matrix res = new Matrix(1, 1);
        res.setElementAt(result, 0, 0);
        return res;
    }

    @Override
    public double getValueAt(Double point) {
        return 0;
    }

    @Override
    public Matrix getValueOfGradient(Matrix point) {
        return null;
    }

    @Override
    public int getNumberOfGradientCalls() {
        return 0;
    }

    @Override
    public void resetNumberOfGradientCalls() {

    }

    @Override
    public int getNumberOfCalls() {
        return 0;
    }

    @Override
    public void resetNumberOfCalls() {

    }

    @Override
    public void setInputVector(Matrix vector, int i) {

    }

    @Override
    public Matrix getHessValue(Matrix point) {
        return null;
    }

    @Override
    public int getNumberOfHessCalls() {
        return 0;
    }

    @Override
    public void resetNumberOfHessCalls() {

    }

    @Override
    public double getLambdaValue(double lam, Matrix v, Matrix currentPoint) {
        return 0;
    }
}

class TransformedFunction implements GoalFunction {
    private GoalFunction goalFunction;
    private List<LimitationFunction> g;
    private List<LimitationFunction> h;
    private double t;
    private final double notSatisfied = 9999999;

    public TransformedFunction(GoalFunction goalFunction, List<LimitationFunction> g, List<LimitationFunction> h, double t) {
        this.goalFunction = goalFunction;
        this.g = g;
        this.h = h;
        this.t = t;
    }

    @Override
    public Matrix getValueAt(Matrix point) {
        double result = goalFunction.getValueAt(point).getElementAt(0, 0);
        for (LimitationFunction fun : g) {
            double x = fun.getResult(point);
            if (x < 1E-6) {
                result = result + notSatisfied;
            } else {
                if (Math.log(x) > result) {
                    result = result + (1 / t) * Math.log(x);
                } else {
                    result = result - (1 / t) * Math.log(x);
                }
            }
        }

        for (LimitationFunction fun : h) {
            double x = fun.getResult(point);
            if (Math.abs(x) < 1E-6) continue;
            result = result + t * x * x;
        }
        Matrix res = new Matrix(1, 1);
        res.setElementAt(result, 0, 0);
        return res;
    }

    @Override
    public double getValueAt(Double point) {
        return goalFunction.getValueAt(point);
    }

    @Override
    public Matrix getValueOfGradient(Matrix point) {
        return goalFunction.getValueOfGradient(point);
    }

    @Override
    public int getNumberOfGradientCalls() {
        return goalFunction.getNumberOfGradientCalls();
    }

    @Override
    public void resetNumberOfGradientCalls() {
        goalFunction.resetNumberOfGradientCalls();
    }

    @Override
    public int getNumberOfCalls() {
        return goalFunction.getNumberOfCalls();
    }

    @Override
    public void resetNumberOfCalls() {
        goalFunction.resetNumberOfCalls();
    }

    @Override
    public void setInputVector(Matrix vector, int i) {

    }

    @Override
    public Matrix getHessValue(Matrix point) {
        return goalFunction.getHessValue(point);
    }

    @Override
    public int getNumberOfHessCalls() {
        return goalFunction.getNumberOfHessCalls();
    }

    @Override
    public void resetNumberOfHessCalls() {
        goalFunction.resetNumberOfHessCalls();
    }

    @Override
    public double getLambdaValue(double lam, Matrix v, Matrix currentPoint) {
        return goalFunction.getLambdaValue(lam, v, currentPoint);
    }
}
