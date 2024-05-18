import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjectAnalyzer {

    public static String analyzeObject(Object obj, List<Method> methodsList) {
        StringBuilder result = new StringBuilder();

        Class<?> cls = obj.getClass();

        // Реальний тип об'єкта
        result.append("Class Name: ").append(cls.getName()).append("\n");

        // Поля та їх значення
        result.append("Fields:\n");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Дозволяємо доступ до приватних полів
            try {
                result.append("\t").append(Modifier.toString(field.getModifiers())).append(" ")
                        .append(field.getType().getName()).append(" ").append(field.getName())
                        .append(" = ").append(field.get(obj)).append("\n");
            } catch (IllegalAccessException e) {
                result.append("\t").append(Modifier.toString(field.getModifiers())).append(" ")
                        .append(field.getType().getName()).append(" ").append(field.getName())
                        .append(" = [access denied]").append("\n");
            }
        }

        // Відкриті методи без параметрів, виключаючи методи з класу Object
        result.append("Methods:\n");
        Method[] methods = cls.getDeclaredMethods();
        int methodIndex = 1;
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && Modifier.isPublic(method.getModifiers())) {
                result.append(methodIndex).append(") ")
                        .append(Modifier.toString(method.getModifiers())).append(" ")
                        .append(method.getReturnType().getName()).append(" ")
                        .append(method.getName()).append("()\n");
                methodsList.add(method);
                methodIndex++;
            }
        }

        return result.toString();
    }

    public static Object invokeMethod(Object obj, Method method) {
        try {
            return method.invoke(obj);
        } catch (Exception e) {
            System.out.println("Error invoking method " + method.getName() + ": " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        // Приклад класу для тестування
        class TestClass {
            private double x = 3.0;
            private double y = 4.0;

            public double Dist() {
                return Math.sqrt(x * x + y * y);
            }

            public void setRandomData() {
                this.x = Math.random() * 10;
                this.y = Math.random() * 10;
            }

            @Override
            public String toString() {
                return "TestClass[x=" + x + ", y=" + y + "]";
            }

            public void setData(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }

        // Створення об'єкта для аналізу
        TestClass testObject = new TestClass();

        // Збереження списку методів
        List<Method> methodsList = new ArrayList<>();

        // Аналіз об'єкта
        System.out.println("Створення об'єкту...");
        System.out.println("Стан об'єкту:");
        System.out.println(analyzeObject(testObject, methodsList));

        // Взаємодія з користувачем для виклику методів
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виклик методу...");
        System.out.println("Список відкритих методів:");

        int methodIndex = 1;
        for (Method method : methodsList) {
            System.out.println(methodIndex + "). " + Modifier.toString(method.getModifiers()) + " "
                    + method.getReturnType().getName() + " " + method.getName() + "()");
            methodIndex++;
        }

        System.out.print("Введіть порядковий номер методу [1 ," + (methodsList.size()) + "]: ");
        int selectedMethodIndex = scanner.nextInt();

        if (selectedMethodIndex >= 1 && selectedMethodIndex <= methodsList.size()) {
            Method method = methodsList.get(selectedMethodIndex - 1);
            Object result = invokeMethod(testObject, method);
            System.out.println("Результат виклику методу: " + result);
        } else {
            System.out.println("Невірний номер методу.");
        }
    }
}
