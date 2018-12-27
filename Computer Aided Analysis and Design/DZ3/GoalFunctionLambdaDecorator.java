public class GoalFunctionLambdaDecorator implements GoalFunction{
    private  GoalFunction goalFunction;

    public GoalFunctionLambdaDecorator(GoalFunction goalFunction){
        this.goalFunction=goalFunction;
    }

    @Override
    public Matrix getValueAt(Matrix point) {
        return goalFunction.getValueAt(point);
    }

    @Override
    public double getValueAt(Double point) {
        return goalFunction.getValueAt(point);
    }

    @Override
    public Matrix getValueOfGradient(Matrix point) {
        return goalFunction.getValueOfGradient(point);
    }

    @Override
    public int getNumberOfGradientCalls() {
        return goalFunction.getNumberOfGradientCalls();
    }

    @Override
    public void resetNumberOfGradientCalls() {
        goalFunction.resetNumberOfGradientCalls();
    }

    @Override
    public int getNumberOfCalls() {
        return goalFunction.getNumberOfCalls();
    }

    @Override
    public void resetNumberOfCalls() {
        goalFunction.resetNumberOfCalls();
    }

    @Override
    public void setInputVector(Matrix vector, int i) {
        goalFunction.setInputVector(vector,i);
    }

    @Override
    public Matrix getHessValue(Matrix point) {
        return goalFunction.getHessValue(point);
    }

    @Override
    public int getNumberOfHessCalls() {
        return goalFunction.getNumberOfHessCalls();
    }

    @Override
    public void resetNumberOfHessCalls() {
        goalFunction.resetNumberOfHessCalls();
    }


    @Override
    public double getLambdaValue(double point, Matrix v, Matrix currentPoint) {
        return 0;
    }

    public Matrix getValueAt(Matrix point, int i) throws MatrixSizeIncompatibleException {
        if(i == -1){
            return goalFunction.getValueAt(point);
        }else {
            int n=point.getN();
            if(i>=n) throw new MatrixSizeIncompatibleException("Matrix size error");
            Matrix E = new Matrix(n,n);
            E.setElementAt(1,i,i);
            Matrix newPoint = E.multiply(point);
            return newPoint;
            //return goalFunction.getValueAt(newPoint).getElementAt(0,0);
        }
    }
}
