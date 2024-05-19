package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class Library implements Externalizable {
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(bookStores.size());
        for (BookStore bookStore : bookStores) {
            bookStore.writeExternal(out);
        }
        out.writeInt(registeredReaders.size());
        for (BookReader bookReader : registeredReaders) {
            bookReader.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        int bookStoreCount = in.readInt();
        bookStores = new java.util.ArrayList<>();
        for (int i = 0; i < bookStoreCount; i++) {
            BookStore bookStore = new BookStore();
            bookStore.readExternal(in);
            bookStores.add(bookStore);
        }
        int readerCount = in.readInt();
        registeredReaders = new java.util.ArrayList<>();
        for (int i = 0; i < readerCount; i++) {
            BookReader bookReader = new BookReader();
            bookReader.readExternal(in);
            registeredReaders.add(bookReader);
        }
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

