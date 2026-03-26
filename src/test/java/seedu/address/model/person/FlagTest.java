package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FlagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Flag(null));
    }

    @Test
    public void constructor_invalidFlag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Flag(""));
        assertThrows(IllegalArgumentException.class, () -> new Flag("   "));
        assertThrows(IllegalArgumentException.class, () -> new Flag("a".repeat(Flag.MAX_LENGTH + 1)));
    }

    @Test
    public void isValidFlagReason() {
        assertFalse(Flag.isValidFlagReason(null));
        assertFalse(Flag.isValidFlagReason(""));
        assertFalse(Flag.isValidFlagReason("   "));
        assertFalse(Flag.isValidFlagReason("a".repeat(Flag.MAX_LENGTH + 1)));

        assertTrue(Flag.isValidFlagReason("Missing consent form"));
        assertTrue(Flag.isValidFlagReason("Medical note: asthma, needs inhaler access"));
    }

    @Test
    public void equals() {
        Flag flag = new Flag("Missing consent form");

        assertTrue(flag.equals(new Flag("Missing consent form")));
        assertTrue(flag.equals(flag));
        assertFalse(flag.equals(null));
        assertFalse(flag.equals(5));
        assertFalse(flag.equals(new Flag("Parent requested callback")));
    }

    @Test
    public void toStringMethod() {
        Flag flag = new Flag("Missing consent form");
        assertEquals("Missing consent form", flag.toString());
    }
}
