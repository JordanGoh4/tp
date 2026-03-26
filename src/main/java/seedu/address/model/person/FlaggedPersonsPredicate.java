package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} is currently flagged.
 */
public class FlaggedPersonsPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person.getFlag() != null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof FlaggedPersonsPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("matchesFlaggedPersons", true)
                .toString();
    }
}
