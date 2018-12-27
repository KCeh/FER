public class Function3 implements GoalFunction{
    private int numberOfCalls=0;
    private Matrix inputVariables;
    private int index;

    public Function3(){
        inputVariables=new Matrix(1,1);
    }

    public void setInputValues(double element){
        inputVariables.setElementAt(element,0,0);
    }

    @Override
    public Matrix getValueAt(Matrix point) {
        setInputValues(point.getElementAt(0,0));
        double solution = Math.pow(point.getElementAt(0,0)-3,2);
        Matrix sol= new Matrix(1,1);
        sol.setElementAt(solution,0,0);
        numberOfCalls++;
        return sol;
    }

    @Override
    public double getValueAt(Double point) {
        Matrix point1=new Matrix(1,1);
        point1.setElementAt(point,0,0);
        return getValueAt(point1).getElementAt(0,0);
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
