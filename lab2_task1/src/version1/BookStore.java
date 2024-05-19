package version1;

import java.io.Serializable;
import java.util.List;

public class BookStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Book> books;

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

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

