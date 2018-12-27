public interface GoalFunction {

    public Matrix getValueAt(Matrix point);
    public double getValueAt(Double point);
    public int getNumberOfCalls();
    public void resetNumberOfCalls();
    public void setInputVector(Matrix vector,int i);
}
