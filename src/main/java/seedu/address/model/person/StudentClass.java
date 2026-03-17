package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's class in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClass(String)}
 */
public class StudentClass {

    public static final String MESSAGE_CONSTRAINTS =
            "Class names should be alphanumeric and not blank (e.g. 3A, 4B)";

    /*
     * Class names can be alphanumeric, e.g. "3A", "4B", "Primary5".
     * The first character must not be whitespace.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}]*";

    public final String value;

    /**
     * Constructs a {@code StudentClass}.
     *
     * @param studentClass A valid class name.
     */
    public StudentClass(String studentClass) {
        requireNonNull(studentClass);
        checkArgument(isValidClass(studentClass), MESSAGE_CONSTRAINTS);
        value = studentClass.trim();
    }

    /**
     * Returns true if a given string is a valid class name.
     */
    public static boolean isValidClass(String test) {
        return test != null && !test.isBlank() && test.trim().matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof StudentClass)) {
            return false;
        }

        StudentClass otherStudentClass = (StudentClass) other;
        return value.equalsIgnoreCase(otherStudentClass.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }

}
