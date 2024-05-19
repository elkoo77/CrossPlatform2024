package updWork2;

import updWork.User;

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
    private List<User> userList = new ArrayList<>();
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int serverPort;
    private InetAddress serverAddress;
    private ClientGUI gui;

    public UPDClient(String address, int port) {
        gui = new ClientGUI();
        try {
            serverAddress = InetAddress.getByName(address);
            serverPort = port;
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);
        } catch (UnknownHostException e) {
            gui.displayUsers("Error: " + e);
        } catch (SocketException e) {
            gui.displayUsers("Error: " + e);
        }
    }

    public void work(int bufferSize) throws ClassNotFoundException {
        byte[] buffer = new byte[bufferSize];
        try {
            packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            gui.displayUsers("Sending request");

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
            gui.displayUsers("Server is unreachable: " + e);
        } catch (IOException e) {
            gui.displayUsers("Error: " + e);
        } finally {
            socket.close();
        }

        StringBuilder users = new StringBuilder("Registered users: " + userList.size() + "\n");
        for (User user : userList) {
            users.append(user).append("\n");
        }
        gui.displayUsers(users.toString());
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

