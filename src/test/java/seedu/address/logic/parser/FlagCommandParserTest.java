package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REASON_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REASON_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REASON_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_AMY;
import static seedu.address.logic.commands.FlagCommand.MESSAGE_REASON_REQUIRED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.FlagCommand;
import seedu.address.model.person.Flag;

public class FlagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlagCommand.MESSAGE_USAGE);

    private FlagCommandParser parser = new FlagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_FLAG_REASON_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", MESSAGE_REASON_REQUIRED);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1" + REASON_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + REASON_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_REASON_DESC, Flag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + REASON_DESC_AMY + REASON_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REASON));
    }

    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + REASON_DESC_AMY;
        FlagCommand expectedCommand = new FlagCommand(targetIndex, new Flag(VALID_FLAG_REASON_AMY));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_reasonWithLeadingTrailingWhitespace_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REASON + "  " + VALID_FLAG_REASON_AMY + "  ";
        FlagCommand expectedCommand = new FlagCommand(targetIndex, new Flag(VALID_FLAG_REASON_AMY));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
