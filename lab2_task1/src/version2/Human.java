package version2;

import java.io.Serializable;

public abstract class Human implements Serializable {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;

    public Human() {}

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Human{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }
}

