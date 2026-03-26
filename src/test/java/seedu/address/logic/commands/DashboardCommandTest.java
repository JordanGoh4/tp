package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_REASON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FlaggedPersonsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DashboardCommandTest {

    @Test
    public void execute_flaggedPersonsPresent_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person flaggedAlice = new PersonBuilder(ALICE).withFlag(VALID_FLAG_REASON_AMY).build();
        Person flaggedCarl = new PersonBuilder(CARL).withFlag(VALID_FLAG_REASON_BOB).build();
        model.setPerson(ALICE, flaggedAlice);
        model.setPerson(CARL, flaggedCarl);

        DashboardCommand dashboardCommand = new DashboardCommand();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(new FlaggedPersonsPredicate());

        assertCommandSuccess(dashboardCommand, model, buildExpectedDashboard(flaggedAlice, flaggedCarl), expectedModel);
    }

    @Test
    public void execute_noFlaggedPersons_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DashboardCommand dashboardCommand = new DashboardCommand();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(new FlaggedPersonsPredicate());

        assertCommandSuccess(dashboardCommand, model, buildExpectedDashboard(), expectedModel);
    }

    @Test
    public void equals() {
        DashboardCommand dashboardCommand = new DashboardCommand();

        assertTrue(dashboardCommand.equals(dashboardCommand));
        assertTrue(dashboardCommand.equals(new DashboardCommand()));
        assertFalse(dashboardCommand.equals(null));
        assertFalse(dashboardCommand.equals(new ClearCommand()));
    }

    private String buildExpectedDashboard(Person... persons) {
        String lineSeparator = System.lineSeparator();
        StringBuilder builder = new StringBuilder()
                .append("============ FLAGGED CONTACTS ============")
                .append(lineSeparator)
                .append(persons.length)
                .append(" flagged contact")
                .append(persons.length == 1 ? "" : "s")
                .append(" found.");

        if (persons.length > 0) {
            builder.append(lineSeparator).append(lineSeparator);
            for (int i = 0; i < persons.length; i++) {
                Person person = persons[i];
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

                if (i < persons.length - 1) {
                    builder.append(lineSeparator).append(lineSeparator);
                }
            }
        }

        builder.append(lineSeparator).append("==========================================");
        return builder.toString();
    }
}
