package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes one or more people from the address book.
 * Identified using displayed index/indices (i.e. "2", "1 3 4", "1-5"),
 * or all currently displayed ("all")
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more people identified by index numbers in the displayed person list.\n"
            + "Parameters: INDEX... (space-separated), START_INDEX-END_INDEX (range), or 'all'\n"
            + "Examples: " + COMMAND_WORD + " 1, "
            + COMMAND_WORD + " 1 3 5, "
            + COMMAND_WORD + " 2-5, "
            + COMMAND_WORD + " all";

    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC = "The person index %1$s is invalid";
    public static final String MESSAGE_EMPTY_PERSON_LIST = "The displayed list is already empty.";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted %1$s people: %2$s";

    private final ArrayList<Index> targets;
    private final Index rangeStart;
    private final Index rangeEnd;
    private final boolean deleteAllDisplayed;

    /**
     * @param targets list of indices of the people to be deleted
     */
    public DeleteCommand(ArrayList<Index> targets) {
        this.targets = targets;
        this.rangeStart = null;
        this.rangeEnd = null;
        this.deleteAllDisplayed = false;
    }

    /**
     * @param rangeStart the first displayed index to delete
     * @param rangeEnd the last displayed index to delete
     */
    public DeleteCommand(Index rangeStart, Index rangeEnd) {
        this.targets = new ArrayList<>();
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.deleteAllDisplayed = false;
    }

    /**
     * @param deleteAllDisplayed true
     */
    public DeleteCommand(boolean deleteAllDisplayed) {
        this.deleteAllDisplayed = deleteAllDisplayed;
        this.targets = new ArrayList<>();
        this.rangeStart = null;
        this.rangeEnd = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = new ArrayList<>(model.getFilteredPersonList());

        if (deleteAllDisplayed) {
            if (lastShownList.isEmpty()) {
                throw new CommandException(MESSAGE_EMPTY_PERSON_LIST);
            }
            StringBuilder names = new StringBuilder();
            for (Person p : lastShownList) {
                names.append(p.getName()).append(", ");
                model.deletePerson(p);
            }
            names.setLength(names.length() - 2);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, lastShownList.size(), names));
        }

        ArrayList<Index> indicesToDelete = getValidatedIndices(lastShownList);
        StringBuilder names = new StringBuilder();

        for (Index targetIndex : indicesToDelete) {
            Person p = lastShownList.get(targetIndex.getZeroBased());
            names.append(p.getName()).append(", ");
            model.deletePerson(p);
        }
        names.setLength(names.length() - 2);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, indicesToDelete.size(), names));
    }

    private ArrayList<Index> getValidatedIndices(List<Person> lastShownList) throws CommandException {
        if (isRangeDelete()) {
            if (rangeEnd.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(
                        MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC,
                        rangeEnd.getOneBased()));
            }

            ArrayList<Index> rangeTargets = new ArrayList<>();
            for (int i = rangeStart.getOneBased(); i <= rangeEnd.getOneBased(); i++) {
                rangeTargets.add(Index.fromOneBased(i));
            }
            return rangeTargets;
        }

        for (Index targetIndex : targets) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(
                        MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_SPECIFIC,
                        targetIndex.getOneBased()));
            }
        }
        return targets;
    }

    private boolean isRangeDelete() {
        return rangeStart != null && rangeEnd != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return deleteAllDisplayed == otherDeleteCommand.deleteAllDisplayed
                && targets.equals(otherDeleteCommand.targets)
                && Objects.equals(rangeStart, otherDeleteCommand.rangeStart)
                && Objects.equals(rangeEnd, otherDeleteCommand.rangeEnd);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this).add("targetIndex", targets);
        if (isRangeDelete()) {
            builder.add("rangeStart", rangeStart).add("rangeEnd", rangeEnd);
        }
        if (deleteAllDisplayed) {
            builder.add("deleteAllDisplayed", true);
        }
        return builder.toString();
    }
}
