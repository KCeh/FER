public class GradientDescent {
    private double threshold;
    private GoalFunction goalFunction;

    public GradientDescent(double threshold, GoalFunction goalFunction){
        this.threshold=threshold;
        this.goalFunction=goalFunction;
    }

    public GradientDescent(GoalFunction goalFunction){
        this(1E-6,goalFunction);
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double start(Matrix startingPoint, boolean golden) throws MatrixSizeIncompatibleException {
        System.out.println("Gradient descent for x=");
        System.out.println(startingPoint);

        double bestValue=goalFunction.getValueAt(startingPoint).getElementAt(0,0);
        int numberOfIterationsWithoutImprovement=0;
        Matrix oldX;
        Matrix currentX=new Matrix(startingPoint);
        while(true){
            oldX=new Matrix(currentX);
            Matrix diff=goalFunction.getValueOfGradient(oldX);
            if(!golden){
                currentX=oldX.add(diff.multiplyWithScalar(-1));
            }else {
                Matrix v=diff.multiplyWithScalar(-1).multiplyWithScalar(1/ Matrix.calculateNorm(diff));
                GoldenRatio goldenRatio=new GoldenRatio(0,goalFunction,true, v, currentX);
                double lambda=goldenRatio.startLambda(false);
                currentX=oldX.add(v.multiplyWithScalar(lambda));
            }
            double currentValue=goalFunction.getValueAt(currentX).getElementAt(0,0);
            if(currentValue<bestValue){
                bestValue=currentValue;
                numberOfIterationsWithoutImprovement=0;
            }else {
                numberOfIterationsWithoutImprovement++;
            }
            if(Matrix.calculateNorm(diff)<=threshold || numberOfIterationsWithoutImprovement==100) break;
        }
        System.out.println("Min for x=");
        System.out.println(currentX);
        double min=goalFunction.getValueAt(currentX).getElementAt(0,0);
        System.out.println("Min = "+min);
        return min;
    }
}
