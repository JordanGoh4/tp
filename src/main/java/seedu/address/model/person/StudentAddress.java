package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class StudentAddress {

    public static final String MESSAGE_CONSTRAINTS =
            "Address can take any values, and it should not be blank.";

    public final String value;

    /**
     * Constructs a {@code StudentAddress}.
     *
     * @param address A valid address.
     */
    public StudentAddress(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid address.
     * - Any non-empty string (permissive validation)
     */
    public static boolean isValidAddress(String test) {
        return !test.trim().isEmpty();
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

        if (!(other instanceof StudentAddress)) {
            return false;
        }

        StudentAddress otherAddress = (StudentAddress) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
