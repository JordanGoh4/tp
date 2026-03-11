package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the help window showing all available commands and usage examples.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window. Refer to it for the list of commands and usage examples.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
