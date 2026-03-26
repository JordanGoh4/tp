package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnflagCommandTest {

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person flaggedAlice = new PersonBuilder(ALICE).withFlag(VALID_FLAG_REASON_AMY).build();
        model.setPerson(ALICE, flaggedAlice);

        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(UnflagCommand.MESSAGE_UNFLAG_PERSON_SUCCESS, Messages.format(ALICE));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(flaggedAlice, ALICE);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(unflagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFlagged_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unflagCommand, model, UnflagCommand.MESSAGE_PERSON_NOT_FLAGGED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnflagCommand unflagCommand = new UnflagCommand(outOfBoundIndex);

        assertCommandFailure(unflagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnflagCommand unflagFirstCommand = new UnflagCommand(INDEX_FIRST_PERSON);

        assertTrue(unflagFirstCommand.equals(unflagFirstCommand));
        assertFalse(unflagFirstCommand.equals(null));
        assertFalse(unflagFirstCommand.equals(new ClearCommand()));
        assertFalse(unflagFirstCommand.equals(new UnflagCommand(INDEX_SECOND_PERSON)));
    }
}
