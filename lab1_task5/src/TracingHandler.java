import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TracingHandler implements InvocationHandler {
    private final Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        System.out.println(target + "." + method.getName() + "(" + args[0] + ") = " + result);
        return result;
    }
}
