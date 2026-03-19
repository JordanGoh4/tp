package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SUPPORT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_oneTagSpecifiedUnfilteredList_success() throws ParseException {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person taggedPerson = personInList.withTags(VALID_TAG_REP).build();

        Set<Tag> tags = ParserUtil.parseTags(Collections.singleton(VALID_TAG_REP));
        TagCommand tagCommand = new TagCommand(indexLastPerson, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, taggedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleTagsSpecifiedUnfilteredList_success() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        String[] combined = Stream.concat(
                Arrays.stream(new String[]{VALID_TAG_REP, VALID_TAG_SUPPORT}),
                firstPerson.getTags().stream().map(tag -> tag.tagName)
        ).toArray(String[]::new);
        Person taggedPerson = personInList.withTags(combined).build();

        Set<Tag> tags;
        tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_REP, VALID_TAG_SUPPORT));
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, taggedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws ParseException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Set<Tag> tags = ParserUtil.parseTags(Collections.singleton(VALID_TAG_REP));
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws ParseException {
        Set<Tag> tags = ParserUtil.parseTags(Collections.singleton(VALID_TAG_REP));
        final TagCommand standardCommand = new TagCommand(INDEX_FIRST_PERSON, tags);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagCommand(INDEX_SECOND_PERSON, tags)));

        // different descriptor -> returns false
        Set<Tag> otherTags = ParserUtil.parseTags(Collections.singleton(VALID_TAG_SUPPORT));
        assertFalse(standardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, otherTags)));
    }

}
