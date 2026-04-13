package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class StudentName {

    public static final String MESSAGE_CONSTRAINTS =
            "Name may contain letters, spaces, apostrophes ('), and slashes (/), and it should not be blank.";

    /*
     * First character must not be whitespace. Allows ' (e.g. D'Silva) and / (e.g. s/o, d/o).
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}'/][\\p{Alpha}'/ ]*";

    public final String fullName;

    /**
     * Constructs a {@code StudentName}.
     *
     * @param name A valid name.
     */
    public StudentName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     * - Letters, spaces, apostrophes, and slashes (e.g. s/o, d/o); no digits
     * - Not blank
     * - Case-insensitive (handled elsewhere)
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentName)) {
            return false;
        }

        StudentName otherName = (StudentName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
