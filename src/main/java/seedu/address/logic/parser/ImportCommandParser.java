package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an {@code ImportCommand} object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    @Override
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        Path path = Paths.get(trimmedArgs);
        return new ImportCommand(path);
    }
}
