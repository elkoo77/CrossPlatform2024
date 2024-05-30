import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Define the range and step size
        double start = 1.5;
        double end = 6.5;
        double step = 0.05;

        // Load function values from file
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        readFunctionValuesFromFile("function_values.txt", xValues, yValues);

        // Calculate derivative of function and save to file
        List<Double> derivative = calculateDerivative(xValues, yValues);
        saveFunctionValuesToFile("derivative_values_f5.txt", derivative);

        // Evaluate and differentiate the functions
        evaluateAndDifferentiateFunctions(start, end, step);

        System.out.println("Derivative values saved to separate files.");
    }

    private static void evaluateAndDifferentiateFunctions(double start, double end, double step) {
        double[] functions = {0.5, 0.0, 1.0, 1.5};
        String[] filenames = {"derivative_values_f1.txt", "derivative_values_f2.txt", "derivative_values_f3.txt", "derivative_values_f4.txt"};

        for (int i = 0; i < functions.length; i++) {
            double functionValue = functions[i];
            String filename = filenames[i];
            evaluateAndSaveFunction(start, end, step, functionValue, filename);
        }
    }

    private static void evaluateAndSaveFunction(double start, double end, double step, double functionValue, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (double x = start; x <= end; x += step) {
                double derivative = centralDifferenceDerivative(x, step, new Function(functionValue));
                writer.write(derivative + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    private static double centralDifferenceDerivative(double x, double h, Function function) {
        return (function.apply(x + h) - function.apply(x - h)) / (2 * h);
    }

    private static List<Double> calculateDerivative(List<Double> xValues, List<Double> yValues) {
        List<Double> derivative = new ArrayList<>();
        for (int i = 1; i < xValues.size(); i++) {
            double deltaX = xValues.get(i) - xValues.get(i - 1);
            double deltaY = yValues.get(i) - yValues.get(i - 1);
            double derivativeValue = deltaY / deltaX;
            derivative.add(derivativeValue);
        }
        return derivative;
    }

    private static void readFunctionValuesFromFile(String filename, List<Double> xValues, List<Double> yValues) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                xValues.add(Double.parseDouble(parts[0]));
                yValues.add(Double.parseDouble(parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFunctionValuesToFile(String filename, List<Double> values) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (double value : values) {
                writer.write(value + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    private static class Function {
        private final double value;

        public Function(double value) {
            this.value = value;
        }

        public double apply(double x) {
            return Math.exp(-value * x * x) * Math.sin(x);
        }
    }
}
