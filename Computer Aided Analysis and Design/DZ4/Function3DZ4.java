public class Function3DZ4 implements GoalFunction {
    private int numberOfCalls=0;
    private int n, m;

    public Function3DZ4(int n, int m){
        this.n=n;
        this.m=m;
    }

    @Override
    public Matrix getValueAt(Matrix point){
        int pointN=point.getN();
        if(pointN!=n) System.err.println("N and n not same!");
        double solution=0;
        for(int i=0;i<pointN;i++){
            solution+=(point.getElementAt(i,0)-(i+1))*(point.getElementAt(i,0)-(i+1));
        }
        numberOfCalls++;
        Matrix sol=new Matrix(1,1);
        sol.setElementAt(solution,0,0);
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
        numberOfCalls=0;
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
