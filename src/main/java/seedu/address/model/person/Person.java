package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final StudentClass studentClass;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     * {@code studentClass} can be null (e.g. for parents or unassigned students).
     * {@code remark} must not be null; use {@link Remark#EMPTY} for no remark.
     */
    public Person(Name name, Phone phone, Email email, Address address, StudentClass studentClass, Remark remark,
            Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, remark, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.studentClass = studentClass;
        this.remark = remark;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for backward compatibility - creates a Person without a class (class = null) and empty remark.
     * @deprecated Use {@link #Person(Name, Phone, Email, Address, StudentClass, Remark, Set)} instead.
     */
    @Deprecated
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, null, Remark.EMPTY, tags);
    }

    /**
     * Same as full constructor with an empty remark.
     */
    public Person(Name name, Phone phone, Email email, Address address, StudentClass studentClass, Set<Tag> tags) {
        this(name, phone, email, address, studentClass, Remark.EMPTY, tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the person's class. May be null if not set.
     */
    public StudentClass getStudentClass() {
        return studentClass;
    }

    /**
     * Returns the person's remark. Never null; may be {@link Remark#EMPTY}.
     */
    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns the immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && Objects.equals(studentClass, otherPerson.studentClass)
                && remark.equals(otherPerson.remark)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, studentClass, remark, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("studentClass", studentClass)
                .add("remark", remark)
                .add("tags", tags)
                .toString();
    }

}
