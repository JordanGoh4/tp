package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortAddressCommandTest {

    private static final Comparator<Person> EXPECTED_COMPARATOR =
            Comparator.comparing((Person p) -> p.getAddress().value, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortsByAddress_success() {
        expectedModel.sortPersonList(EXPECTED_COMPARATOR);
        assertCommandSuccess(new SortAddressCommand(), model, SortAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

