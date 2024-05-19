package version2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class BookReader extends Human implements Serializable {
    private static final long serialVersionUID = 1L;
    private int registrationNumber;
    private transient List<Book> borrowedBooks;

    public BookReader() {}

    public BookReader(String firstName, String lastName, int registrationNumber, List<Book> borrowedBooks) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = borrowedBooks;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(borrowedBooks.size());
        for (Book book : borrowedBooks) {
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
        borrowedBooks = new java.util.ArrayList<>();
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
            borrowedBooks.add(new Book(title, authors, publicationYear, editionNumber));
        }
    }

    @Override
    public String toString() {
        return "BookReader{" +
                "registrationNumber=" + registrationNumber +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}
