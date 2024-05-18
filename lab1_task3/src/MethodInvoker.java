import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MethodInvoker {

    public static Object invokeMethodByName(Object obj, String methodName, Object... params) throws FunctionNotFoundException {
        Class<?> cls = obj.getClass();
        Method[] methods = cls.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName) && matchParameters(method, params)) {
                try {
                    return method.invoke(obj, params);
                } catch (Exception e) {
                    throw new FunctionNotFoundException("Error invoking method: " + e.getMessage());
                }
            }
        }

        throw new FunctionNotFoundException("Method " + methodName + " with matching parameters not found.");
    }

    private static boolean matchParameters(Method method, Object... params) {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != params.length) {
            return false;
        }

        for (int i = 0; i < paramTypes.length; i++) {
            if (!isAssignable(paramTypes[i], params[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAssignable(Class<?> paramType, Object param) {
        if (param == null) {
            return !paramType.isPrimitive();
        }
        if (paramType.isPrimitive()) {
            paramType = getWrapperClass(paramType);
        }
        return paramType.isAssignableFrom(param.getClass());
    }

    private static Class<?> getWrapperClass(Class<?> primitiveType) {
        if (primitiveType == boolean.class) return Boolean.class;
        if (primitiveType == byte.class) return Byte.class;
        if (primitiveType == char.class) return Character.class;
        if (primitiveType == short.class) return Short.class;
        if (primitiveType == int.class) return Integer.class;
        if (primitiveType == long.class) return Long.class;
        if (primitiveType == float.class) return Float.class;
        if (primitiveType == double.class) return Double.class;
        throw new IllegalArgumentException("Unknown primitive type: " + primitiveType);
    }

    public static void main(String[] args) {
        try {
            // Приклад класу для тестування
            class TestClass {
                private double a = 1.0;

                public double calculate(double x) {
                    return Math.exp(-Math.abs(a) * x) * Math.sin(x);
                }

                public double calculate(double x, int multiplier) {
                    return Math.exp(-Math.abs(a) * x) * Math.sin(x) * multiplier;
                }

                @Override
                public String toString() {
                    return "TestClass [a=" + a + "]";
                }
            }

            // Створення об'єкта для тестування
            TestClass testObject = new TestClass();

            // Виклик методу з одним параметром
            System.out.println("Типи: [double], значення: [1]");
            Object result = invokeMethodByName(testObject, "calculate", 1.0);
            System.out.println("Результат виклику: " + result);

            // Виклик методу з двома параметрами
            System.out.println("Типи: [double, int], значення: [1, 1]");
            result = invokeMethodByName(testObject, "calculate", 1.0, 1);
            System.out.println("Результат виклику: " + result);

        } catch (FunctionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

