package version2;

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
        BookReader reader1 = new BookReader("Dorothea", "Muller", 1, Arrays.asList(book1));
        BookReader reader2 = new BookReader("Roberto", "Vallay", 2, Arrays.asList(book2));

        // Створення бібліотеки
        Library library = new Library("City Library", Arrays.asList(bookStore1), Arrays.asList(reader1, reader2));

        // Серіалізація
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_partial.ser"))) {
            oos.writeObject(library);
            System.out.println("Library has been serialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація
        Library deserializedLibrary = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library_partial.ser"))) {
            deserializedLibrary = (Library) ois.readObject();
            System.out.println("Library has been deserialized.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Виведення інформації про десеріалізовану бібліотеку
        System.out.println("Deserialized Library: " + deserializedLibrary);
    }
}

