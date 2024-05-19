package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class Book implements Externalizable {
    private String title;
    private List<Author> authors;
    private int publicationYear;
    private int editionNumber;

    public Book() {}

    public Book(String title, List<Author> authors, int publicationYear, int editionNumber) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeInt(publicationYear);
        out.writeInt(editionNumber);
        out.writeInt(authors.size());
        for (Author author : authors) {
            author.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        publicationYear = in.readInt();
        editionNumber = in.readInt();
        int authorCount = in.readInt();
        authors = new java.util.ArrayList<>();
        for (int i = 0; i < authorCount; i++) {
            Author author = new Author();
            author.readExternal(in);
            authors.add(author);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", publicationYear=" + publicationYear +
                ", editionNumber=" + editionNumber +
                '}';
    }
}

