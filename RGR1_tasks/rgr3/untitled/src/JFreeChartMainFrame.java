import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JFreeChartMainFrame extends JFrame {
    private final TreeMap<Double, CustomPoint> pointTreeMap = new TreeMap<>();
    private final ChartPanel chartPanel;
    private String userFunction;
    private double a;
    private double start;
    private double stop;
    private double step;
    private XYSeriesCollection dataset;
    private JFreeChart chart;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFreeChartMainFrame frame = new JFreeChartMainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Define the range and step size
        double start = 1.5;
        double end = 6.5;
        double step = 0.05;

        // Evaluate and differentiate the functions
        evaluateAndDifferentiateFunctions(start, end, step);
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

    private static java.util.List<Double> calculateDerivative(java.util.List<Double> xValues, java.util.List<Double> yValues) {
        java.util.List<Double> derivative = new ArrayList<>();
        for (int i = 1; i < xValues.size(); i++) {
            double deltaX = xValues.get(i) - xValues.get(i - 1);
            double deltaY = yValues.get(i) - yValues.get(i - 1);
            double derivativeValue = deltaY / deltaX;
            derivative.add(derivativeValue);
        }
        return derivative;
    }

    private static void readFunctionValuesFromFile(String filename, java.util.List<Double> xValues, java.util.List<Double> yValues) {
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

    private final JTextField textFieldA;
    private final JTextField textFieldStart;
    private final JTextField textFieldStop;
    private final JTextField textFieldStep;
    private final JTextField textFieldFunction;

    public JFreeChartMainFrame() {
        setResizable(false);
        setTitle("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.pink); // Задаємо колір фону
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelTopButtons = new JPanel();
        panelTopButtons.setBackground(Color.pink); // Задаємо колір фону
        panelTopButtons.setLayout(new GridLayout(1, 0, 5, 0)); // Розташовуємо кнопки в ряд
        contentPane.add(panelTopButtons, BorderLayout.NORTH);

        JLabel parameterLabel = new JLabel("Parameter:");
        parameterLabel.setForeground(Color.black); // Задаємо колір тексту
        textFieldA = new JTextField("1.0");
        panelTopButtons.add(parameterLabel);
        panelTopButtons.add(textFieldA);
        textFieldA.setColumns(10);

        JLabel functionLabel = new JLabel("Function:");
        functionLabel.setForeground(Color.black); // Задаємо колір тексту
        textFieldFunction = new JTextField();
        panelTopButtons.add(functionLabel);
        panelTopButtons.add(textFieldFunction);
        textFieldFunction.setColumns(10);

        JLabel startLabel = new JLabel("Start:");
        startLabel.setForeground(Color.black); // Задаємо колір тексту
        textFieldStart = new JTextField();
        panelTopButtons.add(startLabel);
        panelTopButtons.add(textFieldStart);
        textFieldStart.setColumns(10);

        JLabel stopLabel = new JLabel("Stop:");
        stopLabel.setForeground(Color.black); // Задаємо колір тексту
        textFieldStop = new JTextField();
        panelTopButtons.add(stopLabel);
        panelTopButtons.add(textFieldStop);
        textFieldStop.setColumns(10);

        JLabel stepLabel = new JLabel("Step:");
        stepLabel.setForeground(Color.black); // Задаємо колір тексту
        textFieldStep = new JTextField();
        panelTopButtons.add(stepLabel);
        panelTopButtons.add(textFieldStep);
        textFieldStep.setColumns(10);

        JPanel panelBottomButtons = new JPanel();
        panelBottomButtons.setBackground(Color.PINK); // Задаємо колір фону
        panelBottomButtons.setLayout(new GridLayout(1, 5, 5, 0));
        contentPane.add(panelBottomButtons, BorderLayout.SOUTH);

        JButton btnPlot = new JButton("PLOT");
        btnPlot.setBackground(Color.white); // Задаємо колір фону
        btnPlot.setForeground(Color.black); // Задаємо колір тексту
        btnPlot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chartPanel.setChart(createChart());
            }
        });
        panelBottomButtons.add(btnPlot);

        JButton btnOpenFile = new JButton("OPEN");
        btnOpenFile.setBackground(Color.white); // Задаємо колір фону
        btnOpenFile.setForeground(Color.black); // Задаємо колір тексту
        btnOpenFile.addActionListener(e -> readFromFile());
        panelBottomButtons.add(btnOpenFile);

        JButton btnWriteToFile = new JButton("WRITE TO FILE");
        btnWriteToFile.setBackground(Color.white); // Задаємо колір фону
        btnWriteToFile.setForeground(Color.black); // Задаємо колір тексту
        btnWriteToFile.addActionListener(e -> writeToFile());
        panelBottomButtons.add(btnWriteToFile);

        JButton btnOpenFunctions = new JButton("OPEN FUNCTIONS");
        btnOpenFunctions.setBackground(Color.white); // Задаємо колір фону
        btnOpenFunctions.setForeground(Color.black); // Задаємо колір тексту
        btnOpenFunctions.addActionListener(e -> openFunctions());
        panelBottomButtons.add(btnOpenFunctions);

        JButton btnExit = new JButton("CLOSE");
        btnExit.setBackground(Color.white); // Задаємо колір фону
        btnExit.setForeground(Color.black); // Задаємо колір тексту
        btnExit.addActionListener(e -> System.exit(0));
        panelBottomButtons.add(btnExit);

        XYSeries series = new XYSeries("function");
        XYSeries xySeries = new XYSeries("derivative");
        XYSeriesCollection xyDataset = new XYSeriesCollection(series);
        xyDataset.addSeries(xySeries);
        String userFunction = "";
        JFreeChart chart = ChartFactory.createXYLineChart(userFunction, "X", "Y",
                xyDataset,
                PlotOrientation.VERTICAL,
                true, true, true);
        chartPanel = new ChartPanel(chart);
        contentPane.add(chartPanel, BorderLayout.CENTER);
    }

    public class CustomPoint {

        private double y;
        private double yDir;

        public CustomPoint(double y, double yDir) {
            this.y = y;
            this.yDir = yDir;
        }

        public double getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getYDir() {
            return yDir;
        }

        public void setYDir(int yDir) {
            this.yDir = yDir;
        }
    }


    private JFreeChart createChart() {
        XYSeries series = new XYSeries("Function");
        XYSeries series2 = new XYSeries("Derivative");
        userFunction = textFieldFunction.getText();
        start = Double.parseDouble(textFieldStart.getText());
        stop = Double.parseDouble(textFieldStop.getText());
        step = Double.parseDouble(textFieldStep.getText());
        a = Double.parseDouble(textFieldA.getText());
        double y;
        double dirY;

        for (double x = start; x < stop; x += step) {
            y = f(a, x, userFunction);
            dirY = fDir(a, x, userFunction);
            series.add(x, y);
            series2.add(x, dirY);
            pointTreeMap.put(x, new CustomPoint(y, dirY));
        }

        dataset = new XYSeriesCollection(series);
        dataset.addSeries(series2);
        chart = ChartFactory.createXYLineChart("y = " + userFunction, "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        return chart;
    }

    private double fDir(double a, double x, String userFunction) {
        Expression expression = new ExpressionBuilder(userFunction)
                .variable("a")
                .variable("x")
                .build();
        expression.setVariable("a", a);
        expression.setVariable("x", x);
        return (expression.setVariable("x", x + 0.0001).evaluate()
                - expression.setVariable("x", x - 0.0001).evaluate()) / 0.0002;
    }


    public double f(double a, double x, String function) {
        Expression expression = new ExpressionBuilder(function)
                .variable("a")
                .variable("x")
                .build();
        expression.setVariable("a", a);
        expression.setVariable("x", x);
        return expression.evaluate();
    }


    public void writeToFile() {
        File selectedFile = new File("Table.csv");
        try (FileWriter fileWriter = new FileWriter(selectedFile);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {
            for (Map.Entry<Double, CustomPoint> item : pointTreeMap.entrySet()) {
                String[] row = new String[]{Double.toString(item.getKey()), Double.toString(item.getValue().getY()),
                        Double.toString(item.getValue().getYDir())};
                csvWriter.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        File file = new File("C:\\Users\\el357\\IdeaProjects\\cross platform programming\\RGR1_tasks\\rgr3\\Table.csv");
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] nextRecord;
            csvReader.readNext();
            pointTreeMap.clear();
            while ((nextRecord = csvReader.readNext()) != null) {
                double x = Double.parseDouble(nextRecord[0]);
                double y = Double.parseDouble(nextRecord[1]);
                double yDir = Double.parseDouble(nextRecord[2]);
                pointTreeMap.put(x, new CustomPoint(y, yDir));
            }
            createChartFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private void createChartFromFile() {
        XYSeries series = new XYSeries("Function");
        XYSeries seriesDef = new XYSeries("Derivative");
        for (Map.Entry<Double, CustomPoint> item : pointTreeMap.entrySet()) {
            series.add((double) item.getKey(), item.getValue().getY());
            seriesDef.add((double) item.getKey(), item.getValue().getYDir());
        }
        dataset = new XYSeriesCollection(series);
        dataset.addSeries(seriesDef);
        chart = ChartFactory.createXYLineChart(userFunction, "x", "y",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, true);
        chartPanel.setChart(chart);
    }

    private void openFunctions() {
        JFrame frame = new JFrame();
        frame.setTitle("Functions");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(2, 2)); // 2x2 grid layout for the charts

        // Define the functions
        Function[] functions = {
                new Function(1.5),
                new Function(0.5),
                new Function(0.0),
                new Function(4.5)
        };

        // Create and add charts for each function
        for (int i = 0; i < functions.length; i++) {
            XYSeries series = new XYSeries("Function " + (i + 1));
            double x = 0.0;
            while (x <= 10.0) { // Adjust the range as needed
                series.add(x, functions[i].apply(x));
                x += 0.1; // Adjust the step size as needed
            }

            XYSeriesCollection dataset = new XYSeriesCollection(series);
            JFreeChart chart = ChartFactory.createXYLineChart("Function " + (i + 1), "X", "Y", dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel);
        }

        frame.setVisible(true);
    }

}
