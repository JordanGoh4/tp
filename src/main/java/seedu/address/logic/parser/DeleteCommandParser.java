package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            if (ParserUtil.isAll(args)) {
                return new DeleteCommand(true);
            }
            String trimmedArgs = args.trim();
            if (trimmedArgs.contains("-")) {
                Index[] bounds = ParserUtil.parseRange(trimmedArgs);
                return new DeleteCommand(bounds[0], bounds[1]);
            }
            ArrayList<Index> targets = ParserUtil.parseIndices(args);
            return new DeleteCommand(targets);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
