package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Locale;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    @Override
    public HelpCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            return new HelpCommand();
        }

        // Only allow a single token (the command word).
        String[] tokens = trimmed.split("\\s+");
        if (tokens.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String target = tokens[0].toLowerCase(Locale.ROOT);
        if (!HelpCommand.COMMAND_USAGES.containsKey(target)) {
            throw new ParseException(String.format(
                    "Unknown command '%s'. Known commands: %s",
                    target,
                    String.join(", ", HelpCommand.COMMAND_USAGES.keySet())));
        }

        return new HelpCommand(target);
    }
}
