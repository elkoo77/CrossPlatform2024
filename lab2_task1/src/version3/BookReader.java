package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class BookReader extends Human implements Externalizable {
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
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(registrationNumber);
        out.writeInt(borrowedBooks.size());
        for (Book book : borrowedBooks) {
            book.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        registrationNumber = in.readInt();
        int bookCount = in.readInt();
        borrowedBooks = new java.util.ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            Book book = new Book();
            book.readExternal(in);
            borrowedBooks.add(book);
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

