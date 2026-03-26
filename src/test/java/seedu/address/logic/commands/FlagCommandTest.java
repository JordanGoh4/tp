package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Flag;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FlagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToFlag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person flaggedPerson = new PersonBuilder(personToFlag).withFlag(VALID_FLAG_REASON_AMY).build();
        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON, new Flag(VALID_FLAG_REASON_AMY));

        String expectedMessage = String.format(FlagCommand.MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(flaggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToFlag, flaggedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(flagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FlagCommand flagCommand = new FlagCommand(outOfBoundIndex, new Flag(VALID_FLAG_REASON_AMY));

        assertCommandFailure(flagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Flag firstFlag = new Flag(VALID_FLAG_REASON_AMY);
        Flag secondFlag = new Flag(VALID_FLAG_REASON_BOB);
        FlagCommand flagFirstCommand = new FlagCommand(INDEX_FIRST_PERSON, firstFlag);

        assertTrue(flagFirstCommand.equals(flagFirstCommand));
        assertFalse(flagFirstCommand.equals(null));
        assertFalse(flagFirstCommand.equals(new ClearCommand()));
        assertFalse(flagFirstCommand.equals(new FlagCommand(INDEX_SECOND_PERSON, firstFlag)));
        assertFalse(flagFirstCommand.equals(new FlagCommand(INDEX_FIRST_PERSON, secondFlag)));
    }
}
