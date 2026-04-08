package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the persons in the address book by a specified field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons.\n"
            + "Parameters (optional): FIELD where FIELD can be 'address' or 'name'.\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SUCCESS_ADDRESS = "Sorted all persons by address";
    public static final String MESSAGE_SUCCESS_NAME = "Sorted all persons by name";

    private static final Comparator<Person> ADDRESS_COMPARATOR =
            Comparator.comparing((Person p) -> p.getAddress().value, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);

    private static final Comparator<Person> NAME_COMPARATOR =
            Comparator.comparing((Person p) -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(p -> p.getAddress().value, String.CASE_INSENSITIVE_ORDER);

    /**
     * Enum representing the field to sort persons by.
     */
    public enum SortField {
        ADDRESS,
        NAME
    }

    private final SortField sortField;

    /**
     * Creates a SortCommand with default sort field (address).
     */
    public SortCommand() {
        this(SortField.ADDRESS);
    }

    /**
     * Creates a SortCommand with the specified sort field.
     */
    public SortCommand(SortField sortField) {
        requireNonNull(sortField);
        this.sortField = sortField;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (sortField == SortField.NAME) {
            model.sortPersonList(NAME_COMPARATOR);
            return new CommandResult(MESSAGE_SUCCESS_NAME);
        }
        model.sortPersonList(ADDRESS_COMPARATOR);
        return new CommandResult(MESSAGE_SUCCESS_ADDRESS);
    }
}

