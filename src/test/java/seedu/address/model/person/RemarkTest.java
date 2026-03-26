package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String tooLong = "a".repeat(Remark.MAX_LENGTH + 1);
        assertThrows(IllegalArgumentException.class, () -> new Remark(tooLong));
    }

    @Test
    public void isValidRemark() {
        assertFalse(Remark.isValidRemark(null));

        assertFalse(Remark.isValidRemark("a".repeat(Remark.MAX_LENGTH + 1)));

        assertTrue(Remark.isValidRemark(""));
        assertTrue(Remark.isValidRemark(" "));
        assertTrue(Remark.isValidRemark("a".repeat(Remark.MAX_LENGTH)));
    }

    @Test
    public void isEmpty() {
        assertTrue(Remark.EMPTY.isEmpty());
        assertTrue(new Remark("").isEmpty());
        assertFalse(new Remark("x").isEmpty());
    }

    @Test
    public void equals() {
        Remark remark = new Remark("VIP");

        assertTrue(remark.equals(new Remark("VIP")));
        assertTrue(remark.equals(remark));
        assertFalse(remark.equals(null));
        assertFalse(remark.equals(5));
        assertFalse(remark.equals(new Remark("Other")));
    }
}
