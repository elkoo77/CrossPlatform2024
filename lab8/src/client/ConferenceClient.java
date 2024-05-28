package client;
import rmi.Participant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ConferenceClient {
    private ConferenceManager stub;
    private JTextField hostText, portText, nameText, familyText, organizationText, reportText, emailText;
    private JTextArea participantsArea;

    public ConferenceClient() {
        try {
            // Assume the server is running on localhost and port 1099
            String host = "localhost";
            int port = 1077;
            Registry registry = LocateRegistry.getRegistry(host, port);
            stub = (ConferenceManager) registry.lookup("ConferenceManager");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void createGUI() {
        JFrame frame = new JFrame("Conference Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(578, 379);
        frame.setLayout(null);

        // Setup the host label and text field
        JLabel hostLabel = new JLabel("Host:");
        hostLabel.setBounds(11, 10, 80, 25);
        frame.add(hostLabel);
        hostText = new JTextField("localhost");
        hostText.setBounds(101, 10, 160, 25);
        frame.add(hostText);

        // Setup the port label and text field
        JLabel portLabel = new JLabel("Port:");
        portLabel.setBounds(301, 10, 80, 25);
        frame.add(portLabel);
        portText = new JTextField("1077");
        portText.setBounds(361, 10, 100, 25);
        frame.add(portText);

        // Setup the name label and text field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(11, 40, 80, 25);
        frame.add(nameLabel);
        nameText = new JTextField();
        nameText.setBounds(101, 40, 160, 25);
        frame.add(nameText);

        // Setup other fields similarly
        setupTextFields(frame);

        // Setup buttons
        setupButtons(frame);

        // Setup participants area with a scroll pane
        participantsArea = new JTextArea();
        participantsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(participantsArea);
        scrollPane.setBounds(11, 230, 540, 100);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private void setupTextFields(JFrame frame) {
        // Family
        JLabel familyLabel = new JLabel("Family:");
        familyLabel.setBounds(11, 70, 80, 25);
        frame.add(familyLabel);
        familyText = new JTextField();
        familyText.setBounds(101, 70, 160, 25);
        frame.add(familyText);

        // Organization
        JLabel organizationLabel = new JLabel("Organization:");
        organizationLabel.setBounds(11, 100, 80, 25);
        frame.add(organizationLabel);
        organizationText = new JTextField();
        organizationText.setBounds(101, 100, 160, 25);
        frame.add(organizationText);

        // Report
        JLabel reportLabel = new JLabel("Report:");
        reportLabel.setBounds(11, 130, 80, 25);
        frame.add(reportLabel);
        reportText = new JTextField();
        reportText.setBounds(101, 130, 160, 25);
        frame.add(reportText);

        // Email
        JLabel emailLabel = new JLabel("E-mail:");
        emailLabel.setBounds(11, 160, 80, 25);
        frame.add(emailLabel);
        emailText = new JTextField();
        emailText.setBounds(101, 160, 160, 25);
        frame.add(emailText);
    }

    private void setupButtons(JFrame frame) {
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(11, 200, 100, 25);
        registerButton.addActionListener(this::registerParticipant);
        frame.add(registerButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(121, 200, 100, 25);
        clearButton.addActionListener(e -> clearFields());
        frame.add(clearButton);

        JButton getInfoButton = new JButton("Get Info");
        getInfoButton.setBounds(231, 200, 100, 25);
        getInfoButton.addActionListener(e -> getParticipants());
        frame.add(getInfoButton);

        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(341, 200, 100, 25);
        finishButton.addActionListener(e -> System.exit(0));
        frame.add(finishButton);
    }

    private void registerParticipant(ActionEvent e) {
        try {
            Participant participant = new Participant(nameText.getText(), familyText.getText(), organizationText.getText(), reportText.getText(), emailText.getText());
            stub.registerParticipant(participant);
            JOptionPane.showMessageDialog(null, "Registration successful");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        nameText.setText("");
        familyText.setText("");
        organizationText.setText("");
        reportText.setText("");
        emailText.setText("");
    }

    private void getParticipants() {
        try {
            participantsArea.setText("");  // Clear the text area before display
            List<Participant> participants = stub.getParticipants();
            StringBuilder sb = new StringBuilder();
            for (Participant p : participants) {
                sb.append(p.toString()).append("\n");
            }
            participantsArea.setText(sb.toString());  // Display participant info
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving participants: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConferenceClient().createGUI());
    }
}
