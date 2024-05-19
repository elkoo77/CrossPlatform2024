package version2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class BookStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private transient List<Book> books;

    public BookStore() {}

    public BookStore(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(books.size());
        for (Book book : books) {
            out.writeObject(book.getTitle());
            out.writeInt(book.getPublicationYear());
            out.writeInt(book.getEditionNumber());
            out.writeInt(book.getAuthors().size());
            for (Author author : book.getAuthors()) {
                out.writeObject(author.getFirstName());
                out.writeObject(author.getLastName());
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int size = in.readInt();
        books = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            String title = (String) in.readObject();
            int publicationYear = in.readInt();
            int editionNumber = in.readInt();
            int authorCount = in.readInt();
            List<Author> authors = new java.util.ArrayList<>();
            for (int j = 0; j < authorCount; j++) {
                String firstName = (String) in.readObject();
                String lastName = (String) in.readObject();
                authors.add(new Author(firstName, lastName));
            }
            books.add(new Book(title, authors, publicationYear, editionNumber));
        }
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

