package server;

import interfaces.Executable;

import java.io.*;
import java.net.Socket;
import javax.swing.JTextArea;

public class ClientHandler extends Thread {
    private Socket socket;
    private JTextArea logArea;

    public ClientHandler(Socket socket, JTextArea logArea) {
        this.socket = socket;
        this.logArea = logArea;
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            logArea.append("Connection established with client: " + socket.getInetAddress() + "\n");

            // Отримуємо ім’я class файлу завдання і зберігаємо його у файл
            String classFile = (String) in.readObject();
            classFile = classFile.replaceFirst("client", "server");
            byte[] classData = (byte[]) in.readObject();

            // Зберігаємо файл
            try (FileOutputStream fos = new FileOutputStream(classFile)) {
                fos.write(classData);
            }

            // Завантажуємо клас завдання
            Executable task = (Executable) in.readObject();

            logArea.append("Connection " + socket + " starting execution...\n");

            // Початок обчислень
            double startTime = System.nanoTime();
            Object output = task.execute();
            double endTime = System.nanoTime();
            double completionTime = endTime - startTime;

            logArea.append("Connection " + socket + " | WORK DONE |\n");

            // Формування об’єкта — результату і відправка клієнту
            ResultImpl result = new ResultImpl(output, completionTime);

            // Відправка класу ResultImpl
            classFile = "C:\\Users\\el357\\IdeaProjects\\cross platform programming\\lab5\\ResultImpl.class";
            out.writeObject(classFile);
            try (FileInputStream fis = new FileInputStream(classFile)) {
                byte[] resultClassData = new byte[fis.available()];
                fis.read(resultClassData);
                out.writeObject(resultClassData);
            }

            out.writeObject(result);

            logArea.append("Connection " + socket + " result sent. Finish connection.\n");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logArea.append("Error handling client: " + e.getMessage() + "\n");
        }
    }
}
