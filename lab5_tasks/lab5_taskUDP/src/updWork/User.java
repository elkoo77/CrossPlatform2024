package updWork;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private InetAddress inetAddress;
    private int port;

    // Конструктор за замовчуванням
    public User() {
    }

    // Конструктор з параметрами
    public User(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    // Гетери та сеттери
    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "User{" +
                "inetAddress=" + inetAddress +
                ", port=" + port +
                '}';
    }

    public static void main(String[] args) {
        try {
            User user = new User(InetAddress.getByName("127.0.0.1"), 8080);
            System.out.println(user);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
