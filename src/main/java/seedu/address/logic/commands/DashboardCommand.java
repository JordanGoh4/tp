package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.FlaggedPersonsPredicate;
import seedu.address.model.person.Person;

/**
 * Shows a summary of all flagged contacts and filters the person list to them.
 */
public class DashboardCommand extends Command {

    public static final String COMMAND_WORD = "dashboard";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a summary of all currently flagged contacts.\n"
            + "Example: " + COMMAND_WORD;

    private static final String HEADER = "============ FLAGGED CONTACTS ============";
    private static final String FOOTER = "==========================================";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(new FlaggedPersonsPredicate());
        return new CommandResult(buildDashboard(model.getFilteredPersonList()));
    }

    private String buildDashboard(List<Person> flaggedPersons) {
        String lineSeparator = System.lineSeparator();
        int count = flaggedPersons.size();

        StringBuilder builder = new StringBuilder()
                .append(HEADER)
                .append(lineSeparator)
                .append(String.format(Messages.MESSAGE_FLAGGED_CONTACTS_OVERVIEW, count, count == 1 ? "" : "s"));

        if (!flaggedPersons.isEmpty()) {
            builder.append(lineSeparator).append(lineSeparator);
            for (int i = 0; i < flaggedPersons.size(); i++) {
                Person person = flaggedPersons.get(i);
                builder.append(i + 1)
                        .append(". ")
                        .append(person.getName());
                if (person.getStudentClass() != null) {
                    builder.append(" (Class ")
                            .append(person.getStudentClass())
                            .append(")");
                }
                builder.append(" - Phone: ")
                        .append(person.getPhone())
                        .append(lineSeparator)
                        .append("   Flag: ")
                        .append(person.getFlag());

                if (i < flaggedPersons.size() - 1) {
                    builder.append(lineSeparator).append(lineSeparator);
                }
            }
        }

        builder.append(lineSeparator).append(FOOTER);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof DashboardCommand;
    }
}
