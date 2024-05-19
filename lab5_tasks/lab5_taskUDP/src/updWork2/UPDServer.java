package updWork2;

import updWork.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UPDServer {
    private List<User> userList = new ArrayList<>();
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    private int port;
    private ServerGUI gui;

    public UPDServer(int port) {
        gui = new ServerGUI();
        try {
            socket = new DatagramSocket(port);
            gui.log("Server start...");
        } catch (IOException e) {
            gui.log("Error: " + e);
        }
    }

    public void work(int bufferSize) {
        try {
            while (true) {
                getUserData(bufferSize);
                log(address, port);
                sendUserData();
            }
        } catch (IOException e) {
            gui.log("Error: " + e);
        } finally {
            gui.log("Server end...");
            socket.close();
        }
    }

    private void log(InetAddress address, int port) {
        gui.log("Request from: " + address.getHostAddress() + " port: " + port);
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
            out.flush();
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

