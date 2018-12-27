public class Function6 implements GoalFunction {
    private int numberOfCalls=0;
    private Matrix inputVariables;
    private int n,m;

    public Function6(int n,int m){
        this.n=n;
        this.m=m;
    }


    @Override
    public Matrix getValueAt(Matrix point) {
        int pointN=point.getN();
        if(pointN!=n) System.err.println("N and n not same!");
        double solution=0.5;
        double sum=0;
        for(int i=0;i<pointN;i++){
            sum+=Math.pow(point.getElementAt(i,0),2);
        }
        double a=Math.pow(Math.sin(Math.sqrt(sum)),2)-0.5;
        double b=Math.pow(1+0.001*sum,2);
        solution+=a/b;
        Matrix sol= new Matrix(1,1);
        sol.setElementAt(solution,0,0);
        numberOfCalls++;
        return sol;
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
        return numberOfCalls;
    }

    @Override
    public void resetNumberOfCalls() {
        numberOfCalls=0;
    }

    @Override
    public void setInputVector(Matrix vector, int i) {
        inputVariables=new Matrix(vector);
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
