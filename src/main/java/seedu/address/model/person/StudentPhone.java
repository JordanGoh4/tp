package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's contact number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class StudentPhone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers and be 4-15 digits long.";

    public static final String VALIDATION_REGEX = "\\d{4,15}";

    public final String value;

    /**
     * Constructs a {@code StudentPhone}.
     *
     * @param phone A valid phone number.
     */
    public StudentPhone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     * - Only digits
     * - 4-15 digits long
     */
    public static boolean isValidPhone(String test) {
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

        if (!(other instanceof StudentPhone)) {
            return false;
        }

        StudentPhone otherPhone = (StudentPhone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
