package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_targetCommand_returnsUsageInline() {
        String target = ListCommand.COMMAND_WORD;
        String expectedFeedback = "Usage for '" + target + "':\n" + ListCommand.MESSAGE_USAGE;

        CommandResult result = new HelpCommand(target).execute(model);

        assertEquals(expectedFeedback, result.getFeedbackToUser());
        assertEquals(false, result.isShowHelp()); // inline, no window
        assertEquals(false, result.isExit());
    }

    @Test
    public void execute_noArg_showsHelpWindow() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.HELP_SUMMARY, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noArg_includesMissingImplementedCommandsInSummary() {
        CommandResult result = new HelpCommand().execute(model);

        assertTrue(result.getFeedbackToUser().contains(FilterCommand.MESSAGE_USAGE));
        assertTrue(result.getFeedbackToUser().contains(RemarkCommand.MESSAGE_USAGE));
        assertTrue(result.getFeedbackToUser().contains(TagCommand.MESSAGE_USAGE));
        assertTrue(result.getFeedbackToUser().contains(UndoCommand.MESSAGE_USAGE));
        assertTrue(result.getFeedbackToUser().contains(RedoCommand.MESSAGE_USAGE));
    }
}
