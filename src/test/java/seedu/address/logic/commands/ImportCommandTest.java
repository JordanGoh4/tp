package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    @TempDir
    public Path tempDir;

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validCsv_importsPersons() throws Exception {
        Path csvFile = tempDir.resolve("contacts.csv");
        Files.write(csvFile, List.of(
                "name,phone,email,address,class,tags",
                "Zara Lim,91234567,zara@example.com,\"123, Jurong West Ave 6\",3A,friends;tuition",
                "Noah Tan,92345678,noah@example.com,10th street,,family"
        ));

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        assertEquals("Import finished. Imported: 2, duplicates skipped: 0, invalid rows skipped: 0.",
                result.getFeedbackToUser().trim());
        assertTrue(model.getFilteredPersonList().stream().anyMatch(p -> p.getName().fullName.equals("Zara Lim")));
        assertTrue(model.getFilteredPersonList().stream().anyMatch(p -> p.getName().fullName.equals("Noah Tan")));
    }

    @Test
    public void execute_mixedCsvRows_skipsInvalidAndDuplicates() throws Exception {
        Path csvFile = tempDir.resolve("mixed.csv");
        Files.write(csvFile, List.of(
                "name,phone,email,address,class,tags",
                "Alice Pauline,94351253,alice@example.com,\"123, Jurong West Ave 6, #08-111\",3A,friends",
                "Bad Row,not-a-phone,bad@example.com,Somewhere,3A,friends",
                "New Person,93456789,new@example.com,New Address,4B,school"
        ));

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Import finished. Imported: 1, duplicates skipped: 1, invalid rows skipped: 1."));
        assertTrue(feedback.contains("Row 2 skipped: duplicate person (same name)"));
        assertTrue(feedback.contains("Row 3 skipped:"));
        assertTrue(model.getFilteredPersonList().stream().anyMatch(p -> p.getName().fullName.equals("New Person")));
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        Path missingPath = tempDir.resolve("missing.csv");
        ImportCommand command = new ImportCommand(missingPath);

        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().startsWith("Unable to read CSV file:"));
            return;
        }

        throw new AssertionError("Expected CommandException to be thrown.");
    }

    @Test
    public void execute_unclosedQuotes_skipsRowWithReason() throws Exception {
        Path csvFile = tempDir.resolve("unclosed_quotes.csv");
        Files.write(csvFile, List.of(
                "name,phone,email,address,class,tags",
                "Jordan Goh,12345678,test@gmail.com,\"Blk 357 Ch,3A,friends"
        ));

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Import finished. Imported: 0, duplicates skipped: 0, invalid rows skipped: 1."));
        assertTrue(feedback.contains("Row 2 skipped: Unclosed quotes in CSV line"));
    }

    @Test
    public void execute_invalidColumnCount_skipsRowWithReason() throws Exception {
        Path csvFile = tempDir.resolve("invalid_columns.csv");
        Files.write(csvFile, List.of(
                // Only 3 columns: name,phone,email
                "Bad Row,12,bad@example.com"
        ));

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Import finished. Imported: 0, duplicates skipped: 0, invalid rows skipped: 1."));
        assertTrue(feedback.contains("Row 1 skipped: Invalid CSV column count"));
    }

    @Test
    public void execute_utf8BomInFirstCell_stripsBomAndImports() throws Exception {
        Path csvFile = tempDir.resolve("bom.csv");
        String bomName = "\uFEFFJordan Goh";
        Files.write(csvFile, List.of(
                bomName + ",12345678,test@gmail.com,Blk 357 Ch,3A,friends"
        ));

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser()
                .startsWith("Import finished. Imported: 1, duplicates skipped: 0, invalid rows skipped: 0."));
        assertTrue(model.getFilteredPersonList().stream().anyMatch(p -> p.getName().fullName.equals("Jordan Goh")));
    }

    @Test
    public void execute_manyInvalidRows_onlyFirstTenDetailsIncluded() throws Exception {
        Path csvFile = tempDir.resolve("many_invalid.csv");
        // header + 11 invalid rows -> details should only include 10 rows (MAX_SKIP_DETAILS)
        List<String> lines = new java.util.ArrayList<>();
        lines.add("name,phone,email,address,class,tags");
        for (int i = 0; i < 11; i++) {
            lines.add("Bad " + i + " Person,12,bad" + i + "@example.com,Blk " + i + " Street,3A");
        }
        Files.write(csvFile, lines);

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Import finished. Imported: 0, duplicates skipped: 0, invalid rows skipped: 11."));
        assertTrue(feedback.contains("Details:"));

        int rowOccurrences = feedback.split("Row ").length - 1;
        assertEquals(10, rowOccurrences);
        assertTrue(Pattern.compile("Row 12 skipped:").matcher(feedback).find() == false);
    }

    @Test
    public void execute_malformedFirstRow_headerDetectionFallback() throws Exception {
        Path csvFile = tempDir.resolve("malformed_first_row.csv");
        Files.write(csvFile, List.of(
                "name,12345678,test@gmail.com,\"Blk 357 Ch,3A,friends"
        ));

        ImportCommand command = new ImportCommand(csvFile);
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Import finished. Imported: 0, duplicates skipped: 0, invalid rows skipped: 1."));
        assertTrue(feedback.contains("Row 1 skipped: Unclosed quotes in CSV line"));
    }

    @Test
    public void equalsHashCodeAndToString() {
        Path pathA = tempDir.resolve("a.csv");
        Path pathB = tempDir.resolve("b.csv");
        ImportCommand commandA1 = new ImportCommand(pathA);
        ImportCommand commandA2 = new ImportCommand(pathA);
        ImportCommand commandB = new ImportCommand(pathB);

        assertTrue(commandA1.equals(commandA1));
        assertTrue(commandA1.equals(commandA2));
        assertTrue(!commandA1.equals(commandB));
        assertTrue(!commandA1.equals(null));
        assertTrue(!commandA1.equals("not a command"));
        assertEquals(Objects.hash(pathA), commandA1.hashCode());
        assertTrue(commandA1.toString().contains("csvPath=" + pathA));
    }
}
