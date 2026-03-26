package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnflagCommand;

public class UnflagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnflagCommand.MESSAGE_USAGE);

    private UnflagCommandParser parser = new UnflagCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1", new UnflagCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 extra", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
}
