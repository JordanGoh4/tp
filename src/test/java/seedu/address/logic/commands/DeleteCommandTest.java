package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private ArrayList<Index> toList(Index... indices) {
        ArrayList<Index> list = new ArrayList<>();
        for (Index i : indices) {
            list.add(i);
        }
        return list;
    }

    @Test
    public void execute_validSingleIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(toList(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                1, personToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndicesUnfilteredList_success() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(toList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                2, firstPersonToDelete.getName() + ", " + secondPersonToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validRangeUnfilteredList_success() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                2, firstPersonToDelete.getName() + ", " + secondPersonToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(toList(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, String.format(
                DeleteCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_someInvalidIndicesUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(toList(INDEX_FIRST_PERSON, outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, String.format(
                DeleteCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidLargeRangeUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1000);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, String.format(
                DeleteCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(toList(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                1, personToDelete.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(toList(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, String.format(
                DeleteCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_deleteAll_success() {
        int totalPeople = model.getFilteredPersonList().size();
        DeleteCommand deleteCommand = new DeleteCommand(true);

        ArrayList<Person> allPeople = new ArrayList<>(model.getFilteredPersonList());
        StringBuilder names = new StringBuilder();
        for (Person p : allPeople) {
            names.append(p.getName()).append(", ");
        }
        names.setLength(names.length() - 2);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                allPeople.size(), names);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Person p : new ArrayList<>(expectedModel.getFilteredPersonList())) {
            expectedModel.deletePerson(p);
        }

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(toList(INDEX_FIRST_PERSON));
        DeleteCommand deleteSecondCommand = new DeleteCommand(toList(INDEX_SECOND_PERSON));
        DeleteCommand deleteRangeCommand = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(toList(INDEX_FIRST_PERSON));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different delete mode -> returns false
        assertFalse(deleteFirstCommand.equals(deleteRangeCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteCommand deleteCommand = new DeleteCommand(toList(INDEX_FIRST_PERSON));
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + toList(INDEX_FIRST_PERSON) + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
