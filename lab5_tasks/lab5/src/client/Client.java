package client;

import interfaces.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client {
    private JFrame frame;
    private JTextField ipField, portField, nField;
    private JTextArea resultArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(Client::new);
    }

    public Client() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("TCP Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 750, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);

        JLabel lblIP = new JLabel("IP Address:");
        panel.add(lblIP);

        ipField = new JTextField("localhost");
        panel.add(ipField);
        ipField.setColumns(10);

        JLabel lblPort = new JLabel("Port:");
        panel.add(lblPort);

        portField = new JTextField("12345");
        panel.add(portField);
        portField.setColumns(5);

        JLabel lblN = new JLabel("N:");
        panel.add(lblN);

        nField = new JTextField("27");
        panel.add(nField);
        nField.setColumns(5);

        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitJob();
            }
        });
        panel.add(btnCalculate);

        JButton btnClear = new JButton("Clear Result");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
            }
        });
        panel.add(btnClear);

        JButton btnExit = new JButton("Exit Program");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(btnExit);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void submitJob() {
        String host = ipField.getText();
        int port = Integer.parseInt(portField.getText());
        int num = Integer.parseInt(nField.getText());

        try (Socket client = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {

            resultArea.append("Connected to server\n");

            // Передаємо ім’я class – файлу і сам class – файл на сервер
            String classFile = "C:\\\\Users\\\\el357\\\\IdeaProjects\\\\cross platform programming\\\\lab5\\\\JobOne.class";
            out.writeObject(classFile);
            try (FileInputStream fis = new FileInputStream(classFile)) {
                byte[] classData = new byte[fis.available()];
                fis.read(classData);
                out.writeObject(classData);
            }

            // Формуємо об’єкт — завдання для обчислень
            JobOne aJob = new JobOne(num);
            out.writeObject(aJob);

            resultArea.append("Submitted a job for execution\n");

            // Отримуємо ім’я class – файлу результату та сам class – файл і зберігаємо його у файл
            classFile = (String) in.readObject();
            byte[] resultClassData = (byte[]) in.readObject();
            try (FileOutputStream fos = new FileOutputStream(classFile)) {
                fos.write(resultClassData);
            }

            // Завантаження класу ResultImpl
            Result result = (Result) in.readObject();
            resultArea.append("result = " + result.output() + ", time taken = " + result.scoreTime() + "ns\n");

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            resultArea.append("Error: " + ex.getMessage() + "\n");
        }
    }
}
