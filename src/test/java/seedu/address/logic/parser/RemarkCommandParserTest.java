package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String text = "Allergic to peanuts";
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + text,
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark(text)));
    }

    @Test
    public void parse_blankRemark_clearsToEmpty() {
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK,
                new RemarkCommand(INDEX_FIRST_PERSON, Remark.EMPTY));
    }

    @Test
    public void parse_missingRemarkPrefix_failure() {
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "0 " + PREFIX_REMARK + "hi", MESSAGE_INVALID_FORMAT);
    }
}
