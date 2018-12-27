public class Function1 implements GoalFunction {
    private int numberOfCalls=0;
    private Matrix inputVariables;
    private int index;

    public Function1(){
        inputVariables = new Matrix(2,1);
    }

    @Override
    public Matrix getValueAt(Matrix point) {
        inputVariables=new Matrix(point);
        double x1=inputVariables.getElementAt(0,0);
        double x2=inputVariables.getElementAt(1,0);
        double sol = 100*Math.pow(x2-Math.pow(x1,2),2)+Math.pow(1-x1,2);
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
}
