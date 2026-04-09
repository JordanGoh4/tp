package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {

    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_noArg_returnsNoTargetHelp() throws Exception {
        HelpCommand command = parser.parse("");
        assertEquals(new HelpCommand(), command);
    }

    @Test
    public void parse_validCommandWord_returnsTargetedHelp() throws Exception {
        HelpCommand command = parser.parse("list");
        assertEquals(new HelpCommand("list"), command);
    }

    @Test
    public void parse_missingPreviouslyOmittedCommand_returnsTargetedHelp() throws Exception {
        HelpCommand command = parser.parse("filter");
        assertEquals(new HelpCommand("filter"), command);
    }

    @Test
    public void parse_validCommandWord_caseInsensitive() throws Exception {
        assertEquals(new HelpCommand("list"), parser.parse("LiSt"));
        assertEquals(new HelpCommand("list"), parser.parse("LIST"));
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("unknownCmd"));
    }

    @Test
    public void parse_multipleTokens_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("list extra"));
    }
}
