import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassAnalyzerGUI {
    private JFrame frame;
    private JTextField classNameField;
    private JTextArea resultArea;

    public ClassAnalyzerGUI() {
        frame = new JFrame("Аналізатор класу");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        classNameField = new JTextField(30);
        JButton analyzeButton = new JButton("Аналіз");
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = classNameField.getText();
                String analysisResult = ClassAnalyzer.analyzeClass(className);
                resultArea.setText(analysisResult);
            }
        });

        JButton clearButton = new JButton("Очистити");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classNameField.setText("");
                resultArea.setText("");
            }
        });

        JButton exitButton = new JButton("Завершити");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Введіть повне ім'я класу:"));
        inputPanel.add(classNameField);
        inputPanel.add(analyzeButton);
        inputPanel.add(clearButton);
        inputPanel.add(exitButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClassAnalyzerGUI();
            }
        });
    }
}
