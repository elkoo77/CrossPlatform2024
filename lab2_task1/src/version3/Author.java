package version3;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Author extends Human {
    public Author() {}

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }

    @Override
    public String toString() {
        return "Author{" + "firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + '}';
    }
}

