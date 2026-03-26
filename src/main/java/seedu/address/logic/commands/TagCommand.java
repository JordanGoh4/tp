package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cumulatively adds a new tag to the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_TAG + "sc";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";

    public static final String MESSAGE_NOT_TAGGED = "At least one tag to add must be provided.";

    private final Index index;
    private Set<Tag> tags = Collections.emptySet();

    /**
     * @param index of the person in the filtered person list to edit
     * @param tags tag(s) to add to person
     */
    public TagCommand(Index index, Set<Tag> tags) {
        requireNonNull(index);
        requireNonNull(tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToTag = lastShownList.get(index.getZeroBased());
        Person taggedPerson = addTagsToPerson(personToTag, this.tags);

        model.setPerson(personToTag, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person addTagsToPerson(Person personToTag, Set<Tag> tags) {
        assert personToTag != null;

        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.addAll(tags);

        return new Person(personToTag.getName(),
                personToTag.getPhone(),
                personToTag.getEmail(),
                personToTag.getAddress(),
                personToTag.getStudentClass(),
                personToTag.getFlag(),
                updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return index.equals(otherTagCommand.index)
                && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("tag", tags)
                .toString();
    }
}
