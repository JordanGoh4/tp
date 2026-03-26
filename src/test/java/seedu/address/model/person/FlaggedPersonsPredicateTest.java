package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_AMY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FlaggedPersonsPredicateTest {

    @Test
    public void equals() {
        FlaggedPersonsPredicate predicate = new FlaggedPersonsPredicate();

        assertTrue(predicate.equals(predicate));
        assertTrue(predicate.equals(new FlaggedPersonsPredicate()));
        assertFalse(predicate.equals(1));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_personIsFlagged_returnsTrue() {
        FlaggedPersonsPredicate predicate = new FlaggedPersonsPredicate();
        assertTrue(predicate.test(new PersonBuilder().withFlag(VALID_FLAG_REASON_AMY).build()));
    }

    @Test
    public void test_personIsNotFlagged_returnsFalse() {
        FlaggedPersonsPredicate predicate = new FlaggedPersonsPredicate();
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        FlaggedPersonsPredicate predicate = new FlaggedPersonsPredicate();
        String expected = FlaggedPersonsPredicate.class.getCanonicalName() + "{matchesFlaggedPersons=true}";
        assertEquals(expected, predicate.toString());
    }
}
