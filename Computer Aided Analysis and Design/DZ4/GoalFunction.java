public interface GoalFunction {

    Matrix getValueAt(Matrix point);
    double getValueAt(Double point);
    Matrix getValueOfGradient(Matrix point);
    int getNumberOfGradientCalls();
    void resetNumberOfGradientCalls();
    int getNumberOfCalls();
    void resetNumberOfCalls();
    void setInputVector(Matrix vector, int i);
    Matrix getHessValue(Matrix point);
    int getNumberOfHessCalls();
    void resetNumberOfHessCalls();
    double getLambdaValue(double lam, Matrix v, Matrix currentPoint);
}
