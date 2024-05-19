package updWork;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class UPDClient {
    private List<User> userList = null;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private int serverPort = -1;
    private InetAddress serverAddress = null;

    public UPDClient(String address, int port) {
        userList = new ArrayList<>();
        serverPort = port;
        try {
            serverAddress = InetAddress.getByName(address);
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);
        } catch (UnknownHostException e) {
            System.out.println("Error: " + e);
        } catch (SocketException e) {
            System.out.println("Error: " + e);
        }
    }

    public void work(int bufferSize) throws ClassNotFoundException {
        byte[] buffer = new byte[bufferSize];
        try {
            // Відправка запиту на сервер
            packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Sending request");

            // Прийом відповіді від сервера
            while (true) {
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                if (packet.getLength() == 0) break;

                ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                ObjectInputStream in = new ObjectInputStream(byteStream);
                User usr = (User) in.readObject();
                userList.add(usr);
                clear(buffer);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Server is unreachable: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            socket.close();
        }

        // Виведення списку зареєстрованих користувачів
        System.out.println("Registered users: " + userList.size());
        for (User user : userList) {
            System.out.println(user);
        }
    }

    private void clear(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        (new UPDClient("127.0.0.1", 1501)).work(256);
    }
}
