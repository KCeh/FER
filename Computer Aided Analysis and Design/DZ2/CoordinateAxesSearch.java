public class CoordinateAxesSearch {
    private Matrix threshold;
    private double defaultThreshold=1E-6;
    private Matrix startingPoint;
    private GoalFunction goalFunction;

    //ucitavanje iz datoteke ???

    public CoordinateAxesSearch(Matrix threshold, Matrix startingPoint, GoalFunction goalFunction){
        this.threshold=threshold;
        this.startingPoint=startingPoint;
        this.goalFunction=goalFunction;
    }

    public CoordinateAxesSearch (GoalFunction goalFunction, String fileName){
        this.goalFunction=goalFunction;
        //citaj iz datoteke ...
    }

    public Matrix getThreshold() {
        return threshold;
    }

    public void setThreshold(Matrix threshold) {
        this.threshold = threshold;
    }

    public double start() throws MatrixSizeIncompatibleException {
        System.out.println("Coordinate axes search:");
        Matrix x = new Matrix(startingPoint);
        Matrix xs;
        double lambda;
        GoalFunctionLambdaDecorator lambdaFunction = new GoalFunctionLambdaDecorator(goalFunction);
        GoldenRatio goldenRatio;
        while (true){
            xs = new Matrix(x);
            for(int i=0, n=x.getN();i<n;i++){
                //Matrix goldenRationStartingPoint=lambdaFunction.getValueAt(x,i);
                goldenRatio=new GoldenRatio(x,lambdaFunction,i);
                lambda=goldenRatio.start(false);
                double min=lambda;
                x.setElementAt(min,i,0);
            }
            if(Matrix.calculateDifferenceWithE(x,xs,threshold,defaultThreshold)) break;
        }
        System.out.println("Min for x: ");
        System.out.println(x);
        double min = goalFunction.getValueAt(x).getElementAt(0,0);
        System.out.println("Min= "+min);
        return min;
    }
}
