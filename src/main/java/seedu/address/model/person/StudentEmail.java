package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class StudentEmail {

    public static final String MESSAGE_CONSTRAINTS =
            "Please enter a valid email address (e.g., alex@email.com).";

    /**
     * A simple email validation regex.
     * Format: local@domain
     */
    public static final String VALIDATION_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public final String value;

    /**
     * Constructs a {@code StudentEmail}.
     *
     * @param email A valid email address.
     */
    public StudentEmail(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns true if a given string is a valid email address.
     * - Standard local@domain format
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StudentEmail)) {
            return false;
        }

        StudentEmail otherEmail = (StudentEmail) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
