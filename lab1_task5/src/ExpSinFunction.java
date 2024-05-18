public class ExpSinFunction implements Evaluatable {
    private double a;

    public ExpSinFunction(double a) {
        this.a = a;
    }

    @Override
    public double evalf(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    @Override
    public String toString() {
        return "Exp(-|" + a + "| * x) * sin(x)";
    }
}

