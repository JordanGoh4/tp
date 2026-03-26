package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Optional;

import seedu.address.model.Model;
import seedu.address.ui.HelpWindow;
/**
 * Shows help. With no args, opens the help window. With a command word,
 * returns the usage for that command inline.
 */

public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the help window showing all available commands and usage examples.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window."
            + " Refer to it for the list of commands and usage examples.";

    public static final String HELP_SUMMARY = String.join("\n",
            "Command summary:",
            "----------------------------------------",
            "add    " + AddCommand.MESSAGE_USAGE,
            "edit   " + EditCommand.MESSAGE_USAGE,
            "delete " + DeleteCommand.MESSAGE_USAGE,
            "find   " + FindCommand.MESSAGE_USAGE,
            "export " + ExportCommand.MESSAGE_USAGE,
            "list   " + ListCommand.MESSAGE_USAGE,
            "clear  " + ClearCommand.MESSAGE_USAGE,
            "exit   " + ExitCommand.MESSAGE_USAGE,
            "help   " + HelpCommand.MESSAGE_USAGE,
            "",
            "More details: " + HelpWindow.USERGUIDE_URL
    );

    public static final Map<String, String> COMMAND_USAGES = Map.ofEntries(
            Map.entry(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
            Map.entry(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE),
            Map.entry(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
            Map.entry(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
            Map.entry(ExportCommand.COMMAND_WORD, ExportCommand.MESSAGE_USAGE),
            Map.entry(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE),
            Map.entry(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE),
            Map.entry(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE),
            Map.entry(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE)
    );

    private final Optional<String> targetCommandWord;

    public HelpCommand() {
        this.targetCommandWord = Optional.empty();
    }

    public HelpCommand(String targetCommandWord) {
        this.targetCommandWord = Optional.of(targetCommandWord);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (targetCommandWord.isEmpty()) {
            return new CommandResult(HELP_SUMMARY, true, false);
        }

        String command = targetCommandWord.get();
        String usage = COMMAND_USAGES.get(command);
        String feedback = "Usage for '" + command + "':\n" + usage;
        return new CommandResult(feedback, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HelpCommand)) {
            return false;
        }

        HelpCommand otherCommand = (HelpCommand) other;
        return targetCommandWord.equals(otherCommand.targetCommandWord);
    }

    @Override
    public int hashCode() {
        return targetCommandWord.hashCode();
    }
}
