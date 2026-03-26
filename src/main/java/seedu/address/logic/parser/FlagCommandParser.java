package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FlagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Flag;

/**
 * Parses input arguments and creates a new FlagCommand object.
 */
public class FlagCommandParser implements Parser<FlagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FlagCommand
     * and returns a FlagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FlagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REASON);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlagCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REASON);

        if (!argMultimap.getValue(PREFIX_REASON).isPresent()) {
            throw new ParseException(FlagCommand.MESSAGE_REASON_REQUIRED);
        }

        Flag flag = ParserUtil.parseFlag(argMultimap.getValue(PREFIX_REASON).get());
        return new FlagCommand(index, flag);
    }
}
