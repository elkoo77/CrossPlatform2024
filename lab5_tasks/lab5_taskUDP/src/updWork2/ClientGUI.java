package updWork2;

import javax.swing.*;
import java.awt.*;

public class ClientGUI {
    private JFrame frame;
    private JTextArea textArea;

    public ClientGUI() {
        frame = new JFrame("Client - Registered Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void displayUsers(String users) {
        textArea.setText(users);
    }
}

