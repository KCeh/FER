public class Function3Task2 implements GoalFunction {
    private int numberOfCalls=0;
    private Matrix inputVariables;
    private int index;

    public Function3Task2(){
        inputVariables=new Matrix(5,1);
    }


    @Override
    public Matrix getValueAt(Matrix point) {
        double x1=point.getElementAt(0,0);
        double x2=point.getElementAt(1,0);
        double x3=point.getElementAt(2,0);
        double x4=point.getElementAt(3,0);
        double x5=point.getElementAt(4,0);
        double solution = Math.pow(x1-1,2)+ Math.pow(x2-2,2)+ Math.pow(x3-3,2)+ Math.pow(x4-4,2)+ Math.pow(x5-5,2);
        Matrix sol= new Matrix(1,1);
        sol.setElementAt(solution,0,0);
        numberOfCalls++;
        return sol;
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
