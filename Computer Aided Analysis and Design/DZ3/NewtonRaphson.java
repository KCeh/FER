public class NewtonRaphson {
    private double threshold;
    private GoalFunction goalFunction;

    public NewtonRaphson(double threshold, GoalFunction goalFunction) {
        this.threshold = threshold;
        this.goalFunction = goalFunction;
    }

    public NewtonRaphson(GoalFunction goalFunction) {
        this(1E-6, goalFunction);
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double start(Matrix startingPoint, boolean golden) throws MatrixSizeIncompatibleException, ZeroElementException {
        System.out.println("Newton-Raphson for x=");
        System.out.println(startingPoint);

        double bestValue = goalFunction.getValueAt(startingPoint).getElementAt(0, 0);
        int numberOfIterationsWithoutImprovement = 0;

        Matrix diff;
        Matrix oldX;
        Matrix currentX = new Matrix(startingPoint);
        while (true) {
            oldX=new Matrix(currentX);
            Matrix hess = goalFunction.getHessValue(currentX);
            Matrix gradient = goalFunction.getValueOfGradient(currentX);
            Matrix hessInverse=Matrix.getInverse(hess);
            diff=hessInverse.multiplyWithScalar(-1).multiply(gradient);

            if(golden){
                Matrix v=new Matrix(diff);
                GoldenRatio goldenRatio=new GoldenRatio(0,goalFunction,true, v, currentX);
                double lambda=goldenRatio.startLambda(false);
                currentX=oldX.add(v.multiplyWithScalar(lambda));
            }else {
                currentX=oldX.add(diff);
            }

            double currentValue = goalFunction.getValueAt(currentX).getElementAt(0, 0);
            if (currentValue < bestValue) {
                bestValue = currentValue;
                numberOfIterationsWithoutImprovement = 0;
            } else {
                numberOfIterationsWithoutImprovement++;
            }
            if (Matrix.calculateNorm(diff) <= threshold || numberOfIterationsWithoutImprovement == 100) break;
        }

        System.out.println("Min for x=");
        System.out.println(currentX);
        double min = goalFunction.getValueAt(currentX).getElementAt(0, 0);
        System.out.println("Min = " + min);
        return min;
    }
}
