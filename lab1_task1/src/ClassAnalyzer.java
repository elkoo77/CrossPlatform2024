import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassAnalyzer {

    public static String analyzeClass(String className) {
        try {
            Class<?> cls = Class.forName(className);
            return analyzeClass(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Class not found: " + className;
        }
    }

    public static String analyzeClass(Class<?> cls) {
        StringBuilder result = new StringBuilder();

        // Ім'я пакету
        Package pkg = cls.getPackage();
        result.append("Package: ").append(pkg != null ? pkg.getName() : "(default package)").append("\n");

        // Модифікатори класу
        result.append("Modifiers: ").append(Modifier.toString(cls.getModifiers())).append("\n");

        // Ім'я класу
        result.append("Class Name: ").append(cls.getName()).append("\n");

        // Базовий клас
        Class<?> superClass = cls.getSuperclass();
        result.append("Superclass: ").append(superClass != null ? superClass.getName() : "None").append("\n");

        // Реалізовані інтерфейси
        Class<?>[] interfaces = cls.getInterfaces();
        result.append("Interfaces: ");
        for (Class<?> iface : interfaces) {
            result.append(iface.getName()).append(" ");
        }
        result.append("\n");

        // Поля
        result.append("Fields:\n");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            result.append("\t").append(Modifier.toString(field.getModifiers())).append(" ")
                    .append(field.getType().getName()).append(" ").append(field.getName()).append("\n");
        }

        // Конструктори
        result.append("Constructors:\n");
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            result.append("\t").append(Modifier.toString(constructor.getModifiers())).append(" ")
                    .append(constructor.getName()).append("(");
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                result.append(paramTypes[i].getName());
                if (i < paramTypes.length - 1) {
                    result.append(", ");
                }
            }
            result.append(")\n");
        }

        // Методи
        result.append("Methods:\n");
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            result.append("\t").append(Modifier.toString(method.getModifiers())).append(" ")
                    .append(method.getReturnType().getName()).append(" ").append(method.getName()).append("(");
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                result.append(paramTypes[i].getName());
                if (i < paramTypes.length - 1) {
                    result.append(", ");
                }
            }
            result.append(")\n");
        }

        return result.toString();
    }
}

