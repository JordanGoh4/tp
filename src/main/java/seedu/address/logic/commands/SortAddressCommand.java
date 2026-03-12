package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the persons in the address book by address alphabetically.
 */
public class SortAddressCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by address alphabetically.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons by address";

    private static final Comparator<Person> ADDRESS_COMPARATOR =
            Comparator.comparing((Person p) -> p.getAddress().value, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonList(ADDRESS_COMPARATOR);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

