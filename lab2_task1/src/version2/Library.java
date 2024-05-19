package version2;

import java.io.Serializable;
import java.util.List;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<BookStore> bookStores;
    private List<BookReader> registeredReaders;

    public Library() {}

    public Library(String name, List<BookStore> bookStores, List<BookReader> registeredReaders) {
        this.name = name;
        this.bookStores = bookStores;
        this.registeredReaders = registeredReaders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookStore> getBookStores() {
        return bookStores;
    }

    public void setBookStores(List<BookStore> bookStores) {
        this.bookStores = bookStores;
    }

    public List<BookReader> getRegisteredReaders() {
        return registeredReaders;
    }

    public void setRegisteredReaders(List<BookReader> registeredReaders) {
        this.registeredReaders = registeredReaders;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", bookStores=" + bookStores +
                ", registeredReaders=" + registeredReaders +
                '}';
    }
}

