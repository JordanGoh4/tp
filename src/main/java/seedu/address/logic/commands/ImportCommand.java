package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tag;

/**
 * Imports persons from a CSV file.
 *
 * <p>CSV format per row: name,phone,email,address[,class][,tag1;tag2;...]</p>
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports persons from a CSV file.\n"
            + "Format: " + COMMAND_WORD + " FILE_PATH\n"
            + "CSV columns: name,phone,email,address[,class][,tag1;tag2;...]\n"
            + "Example: " + COMMAND_WORD + " C:\\\\data\\\\contacts.csv";

    public static final String MESSAGE_FILE_ERROR = "Unable to read CSV file: %1$s";
    public static final String MESSAGE_SUCCESS = "Import finished. Imported: %1$d, duplicates skipped: %2$d, "
            + "invalid rows skipped: %3$d.";

    private final Path csvPath;

    public ImportCommand(Path csvPath) {
        requireNonNull(csvPath);
        this.csvPath = csvPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        final List<String> lines;
        try {
            lines = Files.readAllLines(csvPath);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FILE_ERROR, ioe.getMessage()), ioe);
        }

        int importedCount = 0;
        int duplicateCount = 0;
        int invalidCount = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            // Support CSV header row.
            if (i == 0 && isHeaderRow(line)) {
                continue;
            }

            try {
                Person person = parsePersonFromCsvLine(line);
                if (model.hasPerson(person)) {
                    duplicateCount++;
                    continue;
                }
                model.addPerson(person);
                importedCount++;
            } catch (ParseException | IllegalArgumentException ex) {
                invalidCount++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, importedCount, duplicateCount, invalidCount));
    }

    private static boolean isHeaderRow(String line) {
        List<String> columns = parseCsvColumns(line);
        return !columns.isEmpty() && "name".equalsIgnoreCase(columns.get(0).trim());
    }

    private static Person parsePersonFromCsvLine(String line) throws ParseException {
        List<String> columns = parseCsvColumns(line);
        if (columns.size() < 4 || columns.size() > 6) {
            throw new ParseException("Invalid CSV column count");
        }

        Name name = ParserUtil.parseName(columns.get(0));
        Phone phone = ParserUtil.parsePhone(columns.get(1));
        Email email = ParserUtil.parseEmail(columns.get(2));
        Address address = ParserUtil.parseAddress(columns.get(3));

        StudentClass studentClass = null;
        if (columns.size() >= 5 && !columns.get(4).trim().isEmpty()) {
            studentClass = ParserUtil.parseStudentClass(columns.get(4));
        }

        Set<Tag> tags = Set.of();
        if (columns.size() == 6 && !columns.get(5).trim().isEmpty()) {
            String[] rawTags = columns.get(5).split(";");
            List<String> tagValues = new ArrayList<>();
            for (String rawTag : rawTags) {
                String trimmedTag = rawTag.trim();
                if (!trimmedTag.isEmpty()) {
                    tagValues.add(trimmedTag);
                }
            }
            tags = ParserUtil.parseTags(tagValues);
        }

        return new Person(name, phone, email, address, studentClass, tags);
    }

    /**
     * Parses one CSV line, supporting quoted values and escaped quotes.
     */
    private static List<String> parseCsvColumns(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                columns.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        if (inQuotes) {
            throw new IllegalArgumentException("Unclosed quotes in CSV line");
        }

        columns.add(current.toString().trim());
        return columns;
    }
}

