package version2;

public class Author extends Human {
    public Author() {}

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" + "firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + '}';
    }
}

