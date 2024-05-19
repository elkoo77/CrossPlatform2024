package version1;

import java.io.*;
import java.util.Arrays;

public class LibraryDriver {
    public static void main(String[] args) {
        // Створення авторів
        Author author1 = new Author("Napoleon", "Hill");
        Author author2 = new Author("Paulo", "Coelho");

        // Створення книг
        Book book1 = new Book("Think and Grow Rich", Arrays.asList(author1), 1937, 1);
        Book book2 = new Book("The Alchemist", Arrays.asList(author2), 1988, 1);

        // Створення книжкових сховищ
        BookStore bookStore1 = new BookStore("Programming", Arrays.asList(book1, book2));

        // Створення читачів
        BookReader reader1 = new BookReader("Mike", "Meier", 1, Arrays.asList(book1));
        BookReader reader2 = new BookReader("Olga", "Schults", 2, Arrays.asList(book2));

        // Створення бібліотеки
        Library library = new Library("City Library", Arrays.asList(bookStore1), Arrays.asList(reader1, reader2));

        // Виведення інформації про стан бібліотеки
        System.out.println("Поточний стан бібліотеки:");
        System.out.println(library);

        // Серіалізація
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library.ser"))) {
            oos.writeObject(library);
            System.out.println("Бібліотеку серіалізовано.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація
        Library deserializedLibrary = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library.ser"))) {
            deserializedLibrary = (Library) ois.readObject();
            System.out.println("Бібліотеку десеріалізовано.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Виведення інформації про десеріалізовану бібліотеку
        if (deserializedLibrary != null) {
            System.out.println("Десеріалізована бібліотека:");
            System.out.println(deserializedLibrary);
        }
    }
}
