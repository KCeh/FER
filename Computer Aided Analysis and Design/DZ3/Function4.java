public class Function4 implements GoalFunction{
    private int numberOfCalls=0;
    private Matrix inputVariables;
    private int index;
    private int numberOfGradientCalls=0;
    private int numberOfHessCalls=0;

    public Function4(){
        inputVariables = new Matrix(2,1);
    }

    @Override
    public Matrix getValueAt(Matrix point) {
        inputVariables=new Matrix(point);
        double x1=inputVariables.getElementAt(0,0);
        double x2=inputVariables.getElementAt(1,0);
        double sol = Math.pow(x1-3,2)+Math.pow(x2,2);
        Matrix solution = new Matrix(1,1);
        solution.setElementAt(sol,0,0);
        numberOfCalls++;
        return solution;
    }

    @Override
    public double getValueAt(Double point) {
        inputVariables.setElementAt(point,index,0);
        return getValueAt(inputVariables).getElementAt(0,0);
    }

    @Override
    public Matrix getValueOfGradient(Matrix point) {
        double x1=point.getElementAt(0,0);
        double x2=point.getElementAt(1,0);
        double sol1=2*(x1-3);
        double sol2=2*x2;
        Matrix solution = new Matrix(2,1);
        solution.setElementAt(sol1,0,0);
        solution.setElementAt(sol2,1,0);
        numberOfGradientCalls++;
        return solution;
    }

    @Override
    public int getNumberOfGradientCalls() {
        return numberOfGradientCalls;
    }

    @Override
    public void resetNumberOfGradientCalls() {
        numberOfGradientCalls=0;
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
        index=i;
    }

    @Override
    public double getLambdaValue(double lam, Matrix v, Matrix currentPoint) {
        double x1=currentPoint.getElementAt(0,0);
        double x2=currentPoint.getElementAt(1,0);
        double v1=v.getElementAt(0,0);
        double v2=v.getElementAt(1,0);
        x1=x1+lam*v1;
        x2=x2+lam*v2;
        double sol = Math.pow(x1-3,2)+Math.pow(x2,2);
        numberOfCalls++;
        return sol;
    }

    @Override
    public Matrix getHessValue(Matrix point) {
        Matrix hess = new Matrix(2,2);
        double h11=2;
        double h12=0;
        double h21=0;
        double h22=2;
        hess.setElementAt(h11,0,0);
        hess.setElementAt(h12,0,1);
        hess.setElementAt(h21,1,0);
        hess.setElementAt(h22,1,1);
        numberOfHessCalls++;
        return hess;
    }

    @Override
    public int getNumberOfHessCalls() {
        return numberOfHessCalls;
    }

    @Override
    public void resetNumberOfHessCalls() {
        numberOfHessCalls=0;
    }
}
