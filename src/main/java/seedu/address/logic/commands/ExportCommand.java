package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports all persons in the address book to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all persons to a CSV file.\n"
            + "Format: " + COMMAND_WORD + " FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " C:\\\\data\\\\contacts.csv";

    public static final String MESSAGE_SUCCESS = "Exported %1$d persons to: %2$s";
    public static final String MESSAGE_FILE_ERROR = "Unable to write CSV file: %1$s";

    private static final String CSV_HEADER = "name,phone,email,address,class,tags";

    private final Path csvPath;

    /**
     * Constructs an ExportCommand with the given CSV file path.
     *
     * @param csvPath Path to the CSV file to export to.
     */
    public ExportCommand(Path csvPath) {
        requireNonNull(csvPath);
        this.csvPath = csvPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> persons = model.getAddressBook().getPersonList();
        List<String> lines = persons.stream()
                .map(ExportCommand::toCsvRow)
                .collect(Collectors.toList());
        lines.add(0, CSV_HEADER);

        try {
            Path parent = csvPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(csvPath, lines, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FILE_ERROR, ioe.getMessage()), ioe);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, persons.size(), csvPath));
    }

    private static String toCsvRow(Person person) {
        String name = person.getName().fullName;
        String phone = person.getPhone().value;
        String email = person.getEmail().value;
        String address = person.getAddress().value;
        String studentClass = person.getStudentClass() == null ? "" : person.getStudentClass().value;
        String tags = person.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));

        return String.join(",",
                escapeCsv(name),
                escapeCsv(phone),
                escapeCsv(email),
                escapeCsv(address),
                escapeCsv(studentClass),
                escapeCsv(tags));
    }

    private static String escapeCsv(String value) {
        String safe = value == null ? "" : value;
        boolean needsQuotes = safe.contains(",") || safe.contains("\"") || safe.contains("\n") || safe.contains("\r");
        if (!needsQuotes) {
            return safe;
        }
        return "\"" + safe.replace("\"", "\"\"") + "\"";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        ExportCommand otherExportCommand = (ExportCommand) other;
        return csvPath.equals(otherExportCommand.csvPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(csvPath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("csvPath", csvPath)
                .toString();
    }
}

