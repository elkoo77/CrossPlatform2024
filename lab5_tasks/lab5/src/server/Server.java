package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private JFrame frame;
    private JTextArea logArea;
    private JTextField portField;
    private ServerSocket serverSocket;
    private boolean isRunning = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(Server::new);
    }

    public Server() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("TCP Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 650, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);

        JLabel lblPort = new JLabel("Working Port:");
        panel.add(lblPort);

        portField = new JTextField("12345");
        panel.add(portField);
        portField.setColumns(10);

        JButton btnStart = new JButton("Start Server");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        panel.add(btnStart);

        JButton btnStop = new JButton("Stop Server");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
        panel.add(btnStop);

        JButton btnExit = new JButton("Exit Program");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(btnExit);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void startServer() {
        if (isRunning) return;
        int port = Integer.parseInt(portField.getText());
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                logArea.append("Server is listening on port " + port + "\n");
                isRunning = true;
                while (isRunning) {
                    new ClientHandler(serverSocket.accept(), logArea).start();
                }
            } catch (IOException e) {
                logArea.append("Error starting server: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private void stopServer() {
        if (!isRunning) return;
        try {
            isRunning = false;
            serverSocket.close();
            logArea.append("Server stopped.\n");
        } catch (IOException e) {
            logArea.append("Error stopping server: " + e.getMessage() + "\n");
        }
    }
}
