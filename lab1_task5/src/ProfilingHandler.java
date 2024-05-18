import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProfilingHandler implements InvocationHandler {
    private final Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        Object result = method.invoke(target, args);
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Evaluatable.evalf took " + elapsedTime + " ns");
        return result;
    }
}

