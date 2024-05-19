package updWork2;

import javax.swing.*;
import java.awt.*;

public class ServerGUI {
    private JFrame frame;
    private JTextArea textArea;

    public ServerGUI() {
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void log(String message) {
        textArea.append(message + "\n");
    }
}

