package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> StudentName.isValidName(null));

        // invalid name
        assertFalse(StudentName.isValidName("")); // empty string
        assertFalse(StudentName.isValidName(" ")); // spaces only
        assertFalse(StudentName.isValidName("123")); // numbers only
        assertFalse(StudentName.isValidName("peter123")); // contains numbers
        assertFalse(StudentName.isValidName("peter*")); // contains special characters
        assertFalse(StudentName.isValidName("^")); // only non-alphabetic characters

        // valid name
        assertTrue(StudentName.isValidName("peter jack")); // alphabets only
        assertTrue(StudentName.isValidName("peter the great")); // alphabets with spaces
        assertTrue(StudentName.isValidName("Capital Tan")); // with capital letters
        assertTrue(StudentName.isValidName("David Roger Jackson Ray Jr")); // long names
        assertTrue(StudentName.isValidName("John D'Silva")); // apostrophe
        assertTrue(StudentName.isValidName("Ravi s/o Kumar")); // patronymic
    }

    @Test
    public void equals() {
        StudentName name = new StudentName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new StudentName("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new StudentName("Other Valid Name")));
    }

    @Test
    public void toString_method() {
        StudentName name = new StudentName("John Doe");
        String expected = "John Doe";
        assertTrue(name.toString().equals(expected));
    }

    @Test
    public void hashCode_method() {
        StudentName name = new StudentName("John Doe");
        assertTrue(name.hashCode() == name.fullName.hashCode());
    }
}
