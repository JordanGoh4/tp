package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    private ArrayList<Index> toList(Index... indices) {
        ArrayList<Index> list = new ArrayList<>();
        for (Index i : indices) {
            list.add(i);
        }
        return list;
    }

    @Test
    public void parse_validSingleArg_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(toList(INDEX_FIRST_PERSON)));
    }

    @Test
    public void parse_validMultipleArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 2 3",
                new DeleteCommand(toList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON)));
    }

    @Test
    public void parse_validRange_returnsDeleteCommand() {
        assertParseSuccess(parser, "1-3", new DeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_veryLargeRange_returnsDeleteCommandWithoutExpandingRange() {
        assertParseSuccess(parser, "1-1000000000",
                new DeleteCommand(Index.fromOneBased(1), Index.fromOneBased(1000000000)));
    }

    @Test
    public void parse_validAll_returnsDeleteCommand() {
        assertParseSuccess(parser, "all", new DeleteCommand(true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRange_throwsParseException() {
        assertParseFailure(parser, "3-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
