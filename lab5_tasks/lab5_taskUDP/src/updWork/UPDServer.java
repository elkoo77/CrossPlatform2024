package updWork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UPDServer {
    private List<User> userList = null;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private InetAddress address = null;
    private int port = -1;

    public UPDServer(int port) {
        try {
            socket = new DatagramSocket(port);
            userList = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void work(int bufferSize) {
        try {
            System.out.println("Server start...");
            while (true) { // безкінечний цикл роботи з клієнтами
                getUserData(bufferSize); // отримання запиту клієнта
                log(address, port); // вивід інформації про клієнта на екран
                sendUserData(); // формування та відправка відповіді клієнту
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            System.out.println("Server end...");
            socket.close();
        }
    }

    private void log(InetAddress address, int port) {
        System.out.println("Request from: " + address.getHostAddress() + " port: " + port);
    }

    private void clear(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    private void getUserData(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();
        User usr = new User(address, port);
        if (!userList.contains(usr)) {
            userList.add(usr);
        }
        clear(buffer);
    }

    private void sendUserData() throws IOException {
        byte[] buffer;
        for (User user : userList) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(user);
            out.flush(); // ensure all data is written
            buffer = bout.toByteArray();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
        buffer = new byte[0];
        packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    public static void main(String[] args) {
        (new UPDServer(1501)).work(256);
    }
}
