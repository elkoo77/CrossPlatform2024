package version1;

public class Author extends Human {
    private static final long serialVersionUID = 1L;

    public Author() {}

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" + "firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + '}';
    }
}

