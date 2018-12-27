public class LimitationFunction2 implements LimitationFunction {
    @Override
    public double getResult(Matrix point) {
        double x1=point.getElementAt(0,0);
        return 2-x1;
    }
}
