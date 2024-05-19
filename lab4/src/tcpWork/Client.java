package tcpWork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String server;
    private final int port;
    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;

    public Client(String server, int port) {
        this.server = server;
        this.port = port;
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server: " + server + ":" + port);
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void finish() {
        try {
            os.writeObject(new StopOperation());
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            close();
        }
    }

    public void applyOperation(CardOperation op) {
        try {
            os.writeObject(op);
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void close() {
        try {
            if (os != null) os.close();
            if (is != null) is.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client("localhost", 7891);

        while (true) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                client.finish();
                break;
            } else if (command.startsWith("addcard")) {
                AddMetroCardOperation op = new AddMetroCardOperation();
                op.getCrd().setUser(new User("Mike", "Muller", "M", "17.07.1997"));
                op.getCrd().setSerNum("77007");
                op.getCrd().setCollege("KhNU");
                op.getCrd().setBalance(25);
                client.applyOperation(op);
            } else if (command.startsWith("addmoney")) {
                client.applyOperation(new AddMoneyOperation("77007", 100));
            } else if (command.startsWith("showbalance")) {
                client.applyOperation(new ShowBalanceOperation("77007"));
            } else if (command.startsWith("showinfo")) {
                client.applyOperation(new ShowCardInfoOperation("77007"));
            } else if (command.startsWith("paymoney")) {
                client.applyOperation(new PayMoneyOperation("77007", 50));
            } else if (command.startsWith("exportxml")) {
                client.applyOperation(new ExportCardsToXMLOperation("cards.xml"));
            } else {
                System.out.println("Unknown command");
            }
        }

        scanner.close();
    }
}
