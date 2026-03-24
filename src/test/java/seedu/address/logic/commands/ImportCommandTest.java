package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
                result.getFeedbackToUser());
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

        assertEquals("Import finished. Imported: 1, duplicates skipped: 1, invalid rows skipped: 1.",
                result.getFeedbackToUser());
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
}

