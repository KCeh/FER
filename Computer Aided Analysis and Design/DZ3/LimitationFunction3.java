public class LimitationFunction3 implements LimitationFunction {
    @Override
    public double getResult(Matrix point) {
        double x1=point.getElementAt(0,0);
        double x2=point.getElementAt(1,0);
        return 3-x1-x2;
    }
}
