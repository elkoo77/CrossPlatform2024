import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayAndMatrixUtils {

    // Метод для створення одновимірного масиву
    public static Object createArray(Class<?> componentType, int length) {
        return Array.newInstance(componentType, length);
    }

    // Метод для створення матриці
    public static Object createMatrix(Class<?> componentType, int rows, int columns) {
        return Array.newInstance(componentType, rows, columns);
    }

    // Метод для зміни розміру одновимірного масиву зі збереженням значень
    public static Object resizeArray(Object array, int newLength) {
        int length = Array.getLength(array);
        Object newArray = Array.newInstance(array.getClass().getComponentType(), newLength);
        System.arraycopy(array, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }

    // Метод для зміни розміру матриці зі збереженням значень
    public static Object resizeMatrix(Object matrix, int newRows, int newColumns) {
        int rows = Array.getLength(matrix);
        int columns = Array.getLength(Array.get(matrix, 0));
        Object newMatrix = Array.newInstance(matrix.getClass().getComponentType().getComponentType(), newRows, newColumns);

        for (int i = 0; i < Math.min(rows, newRows); i++) {
            Object row = Array.get(matrix, i);
            Object newRow = Array.newInstance(row.getClass().getComponentType(), newColumns);
            System.arraycopy(row, 0, newRow, 0, Math.min(columns, newColumns));
            Array.set(newMatrix, i, newRow);
        }

        return newMatrix;
    }

    // Метод для перетворення одновимірного масиву на рядок
    public static String arrayToString(Object array) {
        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder();
        sb.append(array.getClass().getComponentType().getTypeName())
                .append("[").append(length).append("] = {");

        for (int i = 0; i < length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(Array.get(array, i));
        }
        sb.append("}");
        return sb.toString();
    }

    // Метод для перетворення матриці на рядок
    public static String matrixToString(Object matrix) {
        int rows = Array.getLength(matrix);
        int columns = Array.getLength(Array.get(matrix, 0));
        StringBuilder sb = new StringBuilder();
        sb.append(matrix.getClass().getComponentType().getComponentType().getTypeName())
                .append("[").append(rows).append("][").append(columns).append("] = {");

        for (int i = 0; i < rows; i++) {
            if (i > 0) sb.append(", ");
            sb.append("{");
            Object row = Array.get(matrix, i);
            for (int j = 0; j < columns; j++) {
                if (j > 0) sb.append(", ");
                sb.append(Array.get(row, j));
            }
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Приклади використання

        // Створення одновимірного масиву
        int[] intArray = (int[]) createArray(int.class, 2);
        System.out.println(arrayToString(intArray));

        String[] stringArray = (String[]) createArray(String.class, 3);
        System.out.println(arrayToString(stringArray));

        Double[] doubleArray = (Double[]) createArray(Double.class, 5);
        System.out.println(arrayToString(doubleArray));

        // Створення матриці
        int[][] intMatrix = (int[][]) createMatrix(int.class, 3, 5);
        for (int i = 0; i < intMatrix.length; i++) {
            for (int j = 0; j < intMatrix[0].length; j++) {
                intMatrix[i][j] = i * 10 + j;
            }
        }
        System.out.println(matrixToString(intMatrix));

        // Зміна розміру матриці зі збереженням значень
        intMatrix = (int[][]) resizeMatrix(intMatrix, 4, 6);
        System.out.println(matrixToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 3, 7);
        System.out.println(matrixToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 2, 2);
        System.out.println(matrixToString(intMatrix));
    }
}

