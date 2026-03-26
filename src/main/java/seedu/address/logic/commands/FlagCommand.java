package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Flag;
import seedu.address.model.person.Person;

/**
 * Flags a person as needing follow-up.
 */
public class FlagCommand extends Command {

    public static final String COMMAND_WORD = "flag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Flags the person identified by the index number "
            + "used in the displayed person list with a reason.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_REASON + "REASON\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_REASON + "Missing consent form for field trip";

    public static final String MESSAGE_FLAG_PERSON_SUCCESS = "Flagged Person: %1$s";
    public static final String MESSAGE_REASON_REQUIRED = "A flag reason must be provided.";

    private final Index index;
    private final Flag flag;

    /**
     * @param index of the person in the filtered person list to flag
     * @param flag reason to attach to the person
     */
    public FlagCommand(Index index, Flag flag) {
        requireNonNull(index);
        requireNonNull(flag);

        this.index = index;
        this.flag = flag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFlag = lastShownList.get(index.getZeroBased());
        Person flaggedPerson = flagPerson(personToFlag, flag);

        model.setPerson(personToFlag, flaggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(flaggedPerson)));
    }

    private static Person flagPerson(Person personToFlag, Flag flag) {
        assert personToFlag != null;

        return new Person(personToFlag.getName(),
                personToFlag.getPhone(),
                personToFlag.getEmail(),
                personToFlag.getAddress(),
                personToFlag.getStudentClass(),
                flag,
                personToFlag.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FlagCommand)) {
            return false;
        }

        FlagCommand otherFlagCommand = (FlagCommand) other;
        return index.equals(otherFlagCommand.index)
                && flag.equals(otherFlagCommand.flag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("flag", flag)
                .toString();
    }
}
