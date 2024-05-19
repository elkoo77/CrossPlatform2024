package version1;

import java.io.Serializable;
import java.util.List;

public class BookReader extends Human implements Serializable {
    private static final long serialVersionUID = 1L;
    private int registrationNumber;
    private List<Book> borrowedBooks;

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

    @Override
    public String toString() {
        return "BookReader{" +
                "registrationNumber=" + registrationNumber +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}

