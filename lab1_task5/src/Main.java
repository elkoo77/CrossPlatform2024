public class Main {
    public static void main(String[] args) {
        Evaluatable expSinFunction = new ExpSinFunction(2.5);
        Evaluatable squareFunction = new SquareFunction();

        Evaluatable profilingExpSin = ProxyFactory.createProfilingProxy(expSinFunction, Evaluatable.class);
        Evaluatable tracingExpSin = ProxyFactory.createTracingProxy(expSinFunction, Evaluatable.class);

        Evaluatable profilingSquare = ProxyFactory.createProfilingProxy(squareFunction, Evaluatable.class);
        Evaluatable tracingSquare = ProxyFactory.createTracingProxy(squareFunction, Evaluatable.class);

        double x = 1.0;

        // Використання профілювання
        System.out.println("Calculating F1 with profiling...");
        double resultF1Profiling = profilingExpSin.evalf(x);
        System.out.println("F1: " + resultF1Profiling);

        System.out.println("Calculating F2 with profiling...");
        double resultF2Profiling = profilingSquare.evalf(x);
        System.out.println("F2: " + resultF2Profiling);

        // Використання трасування
        System.out.println("Calculating F1 with tracing...");
        double resultF1Tracing = tracingExpSin.evalf(x);
        System.out.println("F1: " + resultF1Tracing);

        System.out.println("Calculating F2 with tracing...");
        double resultF2Tracing = tracingSquare.evalf(x);
        System.out.println("F2: " + resultF2Tracing);
    }
}
