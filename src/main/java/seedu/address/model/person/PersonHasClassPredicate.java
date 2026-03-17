package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code StudentClass} matches the given class name (case-insensitive).
 */
public class PersonHasClassPredicate implements Predicate<Person> {
    private final String className;

    public PersonHasClassPredicate(String className) {
        this.className = className.trim();
    }

    @Override
    public boolean test(Person person) {
        if (person.getStudentClass() == null) {
            return false;
        }
        return person.getStudentClass().value.equalsIgnoreCase(className);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonHasClassPredicate)) {
            return false;
        }
        PersonHasClassPredicate otherPredicate = (PersonHasClassPredicate) other;
        return className.equalsIgnoreCase(otherPredicate.className);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("className", className).toString();
    }
}
