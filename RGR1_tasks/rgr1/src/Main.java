import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Define the range and step size
        double start = 1.5;
        double end = 6.5;
        double step = 0.05;

        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        // Читаємо дані з файлу
        try (BufferedReader br = new BufferedReader(new FileReader("function_values.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                xValues.add(Double.parseDouble(parts[0])); // Перше значення - незалежна змінна (x)
                yValues.add(Double.parseDouble(parts[1])); // Друге значення - функція (sin(x))
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Обчислюємо похідну функції sin(x)
        List<Double> derivative = calculateDerivative2(xValues, yValues);

        // Зберігаємо значення похідної у відповідний файл
        saveFunctionValuesToFilefortable("derivative_values_f5.txt", derivative);

        // Evaluate and differentiate the functions
        for (double x = start; x <= end; x += step) {
            double derivative_f1 = centralDifferenceDerivative(x, step, Main::function1);
            double derivative_f2 = centralDifferenceDerivative(x, step, Main::function2);
            double derivative_f3 = centralDifferenceDerivative(x, step, Main::function3);
            double derivative_f4 = centralDifferenceDerivative(x, step, Main::function4);


            // Save the function values to separate text files
            saveFunctionValuesToFile("derivative_values_f1.txt", derivative_f1);
            saveFunctionValuesToFile("derivative_values_f2.txt", derivative_f2);
            saveFunctionValuesToFile("derivative_values_f3.txt", derivative_f3);
            saveFunctionValuesToFile("derivative_values_f4.txt", derivative_f4);

        }

        System.out.println("Derivative values saved to separate files.");
    }



    private static double function1(double x) {
        return Math.exp(-x * x) * Math.sin(x);
    }

    private static double function2(double x) {
        return Math.exp(-0.5 * x * x) * Math.sin(x);
    }

    private static double function3(double x) {
        return Math.exp(-1.0 * x * x) * Math.sin(x);
    }

    private static double function4(double x) {
        return Math.exp(-1.5 * x * x) * Math.sin(x);
    }



    private static double centralDifferenceDerivative(double x, double h, Function function) {
        return (function.apply(x + h) - function.apply(x - h)) / (2 * h);
    }

    // Метод для обчислення похідної
    private static List<Double> calculateDerivative2(List<Double> xValues, List<Double> yValues) {
        List<Double> derivative = new ArrayList<>();
        for (int i = 1; i < xValues.size(); i++) {
            double deltaX = xValues.get(i) - xValues.get(i - 1);
            double deltaY = yValues.get(i) - yValues.get(i - 1);
            double derivativeValue = deltaY / deltaX;
            derivative.add(derivativeValue);
        }
        return derivative;
    }
    private static void saveFunctionValuesToFile(String filename, double value) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(value + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    private static void saveFunctionValuesToFilefortable(String filename, List<Double> values) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (double value : values) {
                writer.write(value + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    @FunctionalInterface
    interface Function {
        double apply(double x);
    }


}
