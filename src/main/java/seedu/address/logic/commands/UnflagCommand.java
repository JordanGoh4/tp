package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes an existing follow-up flag from a person.
 */
public class UnflagCommand extends Command {

    public static final String COMMAND_WORD = "unflag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the flag from the person identified by the "
            + "index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_UNFLAG_PERSON_SUCCESS = "Unflagged Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FLAGGED = "This contact is not flagged.";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to unflag
     */
    public UnflagCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnflag = lastShownList.get(index.getZeroBased());
        if (personToUnflag.getFlag() == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FLAGGED);
        }

        Person unflaggedPerson = removeFlag(personToUnflag);
        model.setPerson(personToUnflag, unflaggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UNFLAG_PERSON_SUCCESS, Messages.format(unflaggedPerson)));
    }

    private static Person removeFlag(Person personToUnflag) {
        assert personToUnflag != null;

        return new Person(personToUnflag.getName(),
                personToUnflag.getPhone(),
                personToUnflag.getEmail(),
                personToUnflag.getAddress(),
                personToUnflag.getStudentClass(),
                null,
                personToUnflag.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnflagCommand)) {
            return false;
        }

        UnflagCommand otherUnflagCommand = (UnflagCommand) other;
        return index.equals(otherUnflagCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
