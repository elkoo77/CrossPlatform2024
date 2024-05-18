import java.lang.reflect.Proxy;

public class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T createProfilingProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class<?>[]{interfaceType},
                new ProfilingHandler(target)
        );
    }

    @SuppressWarnings("unchecked")
    public static <T> T createTracingProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class<?>[]{interfaceType},
                new TracingHandler(target)
        );
    }
}
