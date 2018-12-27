public class LimitationFunction5 implements LimitationFunction{
    @Override
    public double getResult(Matrix point) {
        double x2=point.getElementAt(1,0);
        return x2-1;
    }
}
