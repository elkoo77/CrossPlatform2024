public class SquareFunction implements Evaluatable {
    @Override
    public double evalf(double x) {
        return x * x;
    }

    @Override
    public String toString() {
        return "x * x";
    }
}
