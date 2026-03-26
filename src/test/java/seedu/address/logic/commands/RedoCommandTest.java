package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    @Test
    public void execute_nothingToRedo_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_redoAfterUndo_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        model.saveCurrentState();
        model.deletePerson(model.getFilteredPersonList().get(0));
        model.undoAddressBook();

        // redo should re-delete the person
        expectedModel.deletePerson(expectedModel.getFilteredPersonList().get(0));

        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_newCommandClearsRedo_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // save, delete, undo
        model.saveCurrentState();
        model.deletePerson(model.getFilteredPersonList().get(0));
        model.undoAddressBook();

        // new command clears redo
        model.saveCurrentState();
        model.deletePerson(model.getFilteredPersonList().get(0));

        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
