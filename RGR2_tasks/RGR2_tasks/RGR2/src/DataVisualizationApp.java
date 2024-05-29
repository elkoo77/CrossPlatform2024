import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DataVisualizationApp extends JFrame {
    private DataTableBean dataTable;
    private DataGraphBean dataGraph;

    public DataVisualizationApp() {
        setTitle("JavaBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Компонент для табличного представлення даних
        dataTable = new DataTableBean();
        // Налаштування моделі таблиці з вже існуючими колонками "Date", "x" і "y"
        dataTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Date", "X  Value", "Y Value"} // Оновлені назви колонок
        ));
        // Додавання колонки з датою
        dataTable.addDateColumn();

        // Компонент для графічного представлення даних
        dataGraph = new DataGraphBean();

        // Панель з кнопками
        JPanel buttonPanel = new JPanel();
        JButton openButton = new JButton("Відкрити");
        JButton addDataButton = new JButton("ADD значення");
        JButton deleteDataButton = new JButton("DEL значення");
        JButton plotButton = new JButton("Побудувати графік");
        JButton saveButton = new JButton("Зберегти");
        buttonPanel.add(openButton);
        buttonPanel.add(addDataButton);
        buttonPanel.add(deleteDataButton);
        buttonPanel.add(plotButton);
        buttonPanel.add(saveButton);


        addDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        deleteDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        plotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plotData();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openData();
            }
        });

        // Розміщення компонентів на головному вікні
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(dataTable), BorderLayout.WEST);
        contentPane.add(dataGraph, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void openData() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            dataTable.loadFromXML(file);
            JOptionPane.showMessageDialog(this, "Дані успішно відкрито");
        }
    }

    private void addData() {
        String xValue = JOptionPane.showInputDialog(this, "Введіть значення X:");
        String yValue = JOptionPane.showInputDialog(this, "Введіть значення Y:");

        Object[] rowData = {getCurrentDate(), xValue, yValue}; // Додаємо поточну дату
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.addRow(rowData);
    }

    private void deleteData() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Виберіть рядок для видалення");
        }
    }

    private void plotData() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        int rowCount = model.getRowCount();
        double[] xData = new double[rowCount];
        double[] yData = new double[rowCount];

        for (int i = 0; i < rowCount; i++) {
            xData[i] = Double.parseDouble(model.getValueAt(i, 1).toString());
            yData[i] = Double.parseDouble(model.getValueAt(i, 2).toString());
        }

        dataGraph.setXYData(xData, yData);
    }

    private void saveData() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            dataTable.saveToXML(file);
            JOptionPane.showMessageDialog(this, "Дані успішно збережено");
        }
    }



    // Метод для отримання поточної дати
    private String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        return date.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataVisualizationApp app = new DataVisualizationApp();
                app.setVisible(true);
            }
        });
    }
}
