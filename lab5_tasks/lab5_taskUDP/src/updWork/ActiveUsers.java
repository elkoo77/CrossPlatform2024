package updWork;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ActiveUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<User> users;

    // Конструктор за замовчуванням
    public ActiveUsers() {
        this.users = new ArrayList<>();
    }

    // Метод додавання інформації про підключення комп'ютера
    public void add(User user) {
        users.add(user);
    }

    // Метод перевірки на пустоту
    public boolean isEmpty() {
        return users.isEmpty();
    }

    // Метод отримання кількості зареєстрованих комп'ютерів
    public int size() {
        return users.size();
    }

    // Метод перевірки, чи зареєстрований даний комп'ютер
    public boolean contains(User user) {
        return users.contains(user);
    }

    // Метод отримання зареєстрованого комп'ютера за індексом
    public User get(int index) {
        return users.get(index);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (User u : users) {
            buf.append(u).append("\n");
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        try {
            ActiveUsers activeUsers = new ActiveUsers();
            User user1 = new User(InetAddress.getByName("192.168.1.1"), 8080);
            User user2 = new User(InetAddress.getByName("192.168.1.2"), 9090);

            activeUsers.add(user1);
            activeUsers.add(user2);

            System.out.println("Active Users List:");
            System.out.println(activeUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

