package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a follow-up flag attached to a person.
 * Guarantees: immutable; is valid as declared in {@link #isValidFlagReason(String)}
 */
public class Flag {

    public static final int MAX_LENGTH = 200;
    public static final String MESSAGE_CONSTRAINTS =
            "Flag reasons should not be blank and must be at most " + MAX_LENGTH + " characters long.";

    public final String value;

    /**
     * Constructs a {@code Flag}.
     *
     * @param reason A valid flag reason.
     */
    public Flag(String reason) {
        requireNonNull(reason);
        checkArgument(isValidFlagReason(reason), MESSAGE_CONSTRAINTS);
        value = reason.trim();
    }

    /**
     * Returns true if a given string is a valid flag reason.
     */
    public static boolean isValidFlagReason(String test) {
        return test != null && !test.isBlank() && test.trim().length() <= MAX_LENGTH;
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

        if (!(other instanceof Flag)) {
            return false;
        }

        Flag otherFlag = (Flag) other;
        return value.equals(otherFlag.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
