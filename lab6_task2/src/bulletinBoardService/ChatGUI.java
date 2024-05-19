package bulletinBoardService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Proxy;
import java.net.InetAddress;

public class ChatGUI extends JFrame {
    private JTextField groupField;
    private JTextField portField;
    private JTextField nameField;
    private JTextArea chatArea;
    private JTextField messageField;
    private Messanger messanger = null;

    public ChatGUI() {
        setTitle("Чат конференції");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Верхня панель з налаштуваннями
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));

        topPanel.add(new JLabel("Група:"));
        groupField = new JTextField("224.0.0.1");
        topPanel.add(groupField);

        topPanel.add(new JLabel("Порт:"));
        portField = new JTextField("12345");
        topPanel.add(portField);

        topPanel.add(new JLabel("Ім'я:"));
        nameField = new JTextField("User");
        topPanel.add(nameField);

        JButton connectButton = new JButton("З'єднати");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
        topPanel.add(connectButton);

        JButton disconnectButton = new JButton("Роз'єднати");
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });
        topPanel.add(disconnectButton);

        add(topPanel, BorderLayout.NORTH);

        // Центральна панель з текстовою областю для повідомлень
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Нижня панель з полем введення повідомлень і кнопками
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        bottomPanel.add(messageField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Надіслати");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Додаємо кнопку "Очистити"
        JButton clearButton = new JButton("Очистити");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearChatArea();
            }
        });
        bottomPanel.add(clearButton, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void connect() {
        try {
            InetAddress addr = InetAddress.getByName(groupField.getText());
            int port = Integer.parseInt(portField.getText());
            String name = nameField.getText();

            UITasks uiTasks = (UITasks) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{UITasks.class},
                    new EDTInvocationHandler(new UITasksImpl(messageField, chatArea))
            );

            messanger = new MessangerImpl(addr, port, name, uiTasks);
            messanger.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        if (messanger != null) {
            messanger.stop();
        }
    }

    private void sendMessage() {
        if (messanger != null) {
            messanger.send();
        }
    }

    // Метод для очищення текстової області
    private void clearChatArea() {
        chatArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String input = JOptionPane.showInputDialog(null, "Введіть кількість вікон:", "Налаштування", JOptionPane.QUESTION_MESSAGE);
            int windowCount = 1;
            try {
                windowCount = Integer.parseInt(input);
                if (windowCount < 1) {
                    throw new NumberFormatException("Кількість вікон має бути більше або дорівнювати 1.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Неправильний формат числа. Буде запущено одне вікно.", "Помилка", JOptionPane.ERROR_MESSAGE);
                windowCount = 1;
            }

            for (int i = 0; i < windowCount; i++) {
                ChatGUI chatGUI = new ChatGUI();
                chatGUI.setVisible(true);
                chatGUI.setLocation(100 + (i % 4) * 520, 100 + (i / 4) * 450);
            }
        });
    }
}
