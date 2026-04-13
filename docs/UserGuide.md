---
layout: page
title: TeacherBook CLI User Guide
---

TeacherBook CLI is a **desktop app for managing student and parent contacts, optimized for use via a Command Line Interface** (CLI) while still providing the benefits of a Graphical User Interface (GUI). If you can type fast, TeacherBook CLI can help you manage classroom contact information more efficiently than traditional GUI apps.

## Table of contents {#table-of-contents}

* [Quick start](#quick-start)
* [Features](#features)
  * [Viewing help](#viewing-help)
  * [Adding a contact](#adding-a-contact)
  * [Listing all contacts](#listing-all-contacts)
  * [Sorting all contacts](#sorting-all-contacts)
  * [Importing contacts from CSV](#importing-contacts-from-csv)
  * [Exporting contacts to CSV](#exporting-contacts-to-csv)
  * [Editing a contact](#editing-a-contact)
  * [Filtering contacts by class](#filtering-contacts-by-class)
  * [Locating contacts by name](#locating-contacts-by-name)
  * [Adding a tag cumulatively](#adding-a-tag-cumulatively)
  * [Adding or clearing a remark](#adding-or-clearing-a-remark)
  * [Viewing flagged contacts](#viewing-flagged-contacts)
  * [Flagging a contact for follow-up](#flagging-a-contact-for-follow-up)
  * [Removing a follow-up flag](#removing-a-follow-up-flag)
  * [Undoing the previous change](#undoing-the-previous-change)
  * [Redoing the previous undo](#redoing-the-previous-undo)
  * [Deleting contact(s)](#deleting-contacts)
  * [Clearing all entries](#clearing-all-entries)
  * [Exiting the program](#exiting-the-program)
  * [Saving the data](#saving-the-data)
  * [Editing the data file](#editing-the-data-file)
  * [Archiving data files](#archiving-data-files)
* [FAQ](#faq)
* [Known issues](#known-issues)
* [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start {#quick-start}

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from the [TeacherBook CLI releases page](https://github.com/AY2526S2-CS2103-F09-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `delete 2-5` : Deletes contacts at positions 2 through 5 in the **current** list (inclusive range).

   * `delete all` : Deletes **every contact currently shown** in the list (e.g. the whole list after `list`, or only matches after `find` / `filter`). This is different from `clear`, which removes all contacts in the entire address book regardless of what is displayed.

   * `clear` : Deletes all contacts.

   * `sort` : Sort all contacts by address (default).
   
   * `sort name` : Sort all contacts by name alphabetically.

   * `import C:\data\contacts.csv` : Imports contacts from a CSV file.

   * `export C:\data\contacts.csv` : Exports all contacts to a CSV file.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features {#features}

<div markdown="block" class="alert alert-info">

**ℹ️ Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `list 123`, it will be interpreted as `list`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help` {#viewing-help}

Shows usage information in the help window. You can also target a specific command.

Formats:
- `help` — opens the help window with the full command summary.
- `help COMMAND_WORD` — shows usage for that command (e.g., `help add`, `help list`).

* Both the `help` keyword and the `COMMAND_WORD` argument are **case-insensitive**. e.g. `HELP add`, `help ADD`, and `Help Add` all show the usage for the `add` command.

Tip: The help window includes a link to the full User Guide.


### Adding a contact: `add` {#adding-a-contact}

Adds a contact to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [c/CLASS] [r/REMARK] [t/TAG]…​`

* `CLASS` refers to the contact's class where applicable (e.g. 3A, 4B). Must be alphanumeric.
* **Remark (`r/`):** optional. If given, sets an initial remark; if `r/` is present with only spaces (or empty), the contact has no remark. To add or change remarks later, you can also use **`remark`** or **`edit`** with `r/` (see **Adding or clearing a remark** and **Editing a contact** below).

<div markdown="span" class="alert alert-primary">💡 **Tip:**
A contact can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 c/3A t/criminal`
* `add n/Jane Tan p/91234567 e/jane@example.com a/Blk 1 r/Allergic to dairy t/parent` Adds a contact with an initial remark.

### Listing all contacts : `list` {#listing-all-contacts}

Shows a list of all contacts in the address book.

Format: `list`

### Sorting all contacts : `sort` {#sorting-all-contacts}

Shows a list of all contacts in the address book sorted by the selected field.

Format: `sort [address|name]`
* `sort` and `sort address` sort by address alphabetically.
* `sort name` sorts by name alphabetically.

### Importing contacts from CSV : `import` {#importing-contacts-from-csv}

Imports contacts from a CSV file.

Format: `import FILE_PATH` (absolute path)

CSV row format:
`name,phone,email,address[,class][,tag1;tag2;...]`

CSV import rules:
* Encoding: UTF-8 CSV is expected. A UTF-8 BOM at the start of the first value is detected and handled automatically.
* Header detection: the first row is treated as a header only if the first cell is `name` (case-insensitive), and that row is ignored.
* Duplicate detection: duplicates are detected by `name` only (not by all fields).
* Skip feedback: invalid/duplicate rows are skipped, and up to 10 skipped-row reasons are shown after import.
* Quoting: values containing commas should be wrapped in double quotes.

Notes:
* The first row can be a header (e.g. `name,phone,email,address,class,tags`) and it will be ignored.
* Rows with invalid data are skipped.
* Duplicate contacts (same name) are skipped.
* Up to 10 skipped-row reasons are shown after import.
* Addresses containing commas should be wrapped in double quotes.
* `FILE_PATH` must be an absolute file path (e.g. `C:\data\contacts.csv`).
* Known limitation: because header detection checks only the first cell, a first data row starting with `name` (e.g. `name,91234567,user@example.com,...`) may be treated as a header and skipped without a row-specific reason.
* Tags are optional and should be separated with semicolons (`;`).

Examples:
* `import C:\data\contacts.csv`
* `import C:\Users\Alex\Downloads\new_contacts.csv`

### Exporting contacts to CSV : `export`

Exports all contacts from the address book to a CSV file.

Format: `export FILE_PATH` (absolute path)

Notes:
* `FILE_PATH` must be an absolute file path (e.g. `C:\data\contacts.csv`).
* The command writes a CSV header: `name,phone,email,address,class,tags`.
* If needed, parent folders in the given path are created automatically.
* Existing files at the same path will be overwritten.
* Tags are exported as a semicolon-separated list.

Examples:
* `export C:\data\contacts.csv`
* `export C:\Users\Alex\Desktop\backup\contacts.csv`

### Editing a contact : `edit` {#editing-a-contact}

Edits an existing contact in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/CLASS] [r/REMARK] [t/TAG]…​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* **Tags (`t/TAG`):** supplying `t/` **replaces the entire tag set** for that contact—the new tags are not merged with old ones. Any tag you omit is removed. To **add** tags while keeping existing ones, use the **`tag`** command instead (see **Adding a tag cumulatively** below).
* You can remove all the contact’s tags by typing `t/` without specifying any tags after it.
* You can clear the contact's class by typing `c/` without specifying a value after it.
* **Remark (`r/`):** optional. Sets or updates the remark; typing `r/` with only spaces clears the remark (same behaviour as the **`remark`** command—see **Adding or clearing a remark** below).

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd contact to be `Betsy Crower` and clears all existing tags.
*  `edit 1 c/4B` Edits the class of the 1st contact to `4B`.
*  `edit 3 r/Needs consent form` Updates the remark of the 3rd contact.
*  `edit 1 r/   ` Clears the remark of the 1st contact.

### Filtering contacts by class: `filter` {#filtering-contacts-by-class}

Filters and displays contacts who belong to the specified class.

Format: `filter c/CLASS`

* The filter is case-insensitive. e.g. `c/3a` will match contacts in class `3A`.
* Only contacts with a matching class are shown. Use `list` to show all contacts again.

Examples:
* `filter c/3A` Shows all contacts in class 3A.
* `filter c/4B` Shows all contacts in class 4B.

### Locating contacts by name: `find` {#locating-contacts-by-name}

Finds contacts whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The `find` command word is **case-insensitive**. e.g. `FIND alex`, `Find alex`, and `find alex` all work.
* The search keywords are also case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Adding a tag cumulatively: `tag` {#adding-a-tag-cumulatively}

Adds one or more tags **on top of** what the contact already has (**cumulative**). Existing tags stay unless you remove them elsewhere.

Format: `tag INDEX t/TAG [t/MORE_TAGS]…`

* **Cumulative:** new tags are **added** to the current list; other tags are not cleared.
* To **replace the whole tag set** in one go (or clear all tags with `t/`), use **`edit`** with `t/TAG…​` instead (see **Editing a contact** above)—that command **overwrites** all tags.

Examples:
* `tag 2 t/support` Adds the tag `support` to the 2nd contact's existing tags.
* `tag 5 t/exco t/hons` Adds the tags `exco` and `hons` to the 5th contact's existing tags.

### Adding or clearing a remark: `remark` {#adding-or-clearing-a-remark}

Adds a remark to a contact, or clears the existing remark.

Format: `remark INDEX r/[REMARK]`

* Adds or updates the remark of the contact at the specified `INDEX`.
* You can clear a contact's remark by typing `r/` followed only by spaces.

Examples:
* `remark 1 r/Allergic to peanuts` Adds a remark to the 1st contact.
* `remark 2 r/   ` Clears the remark of the 2nd contact.

### Viewing flagged contacts: `dashboard`

Shows a summary of all currently flagged contacts and filters the displayed list to them.

Format: `dashboard`

Examples:
* `dashboard`

### Flagging a contact for follow-up: `flag` {#flagging-a-contact-for-follow-up}

Flags a contact with a follow-up reason.

Format: `flag INDEX r/REASON`

* Flags the contact at the specified `INDEX`.
* The reason is shown in the dashboard and can be removed later using `unflag`.

Examples:
* `flag 3 r/Missing consent form for field trip`
* `flag 1 r/Parent requested a callback`

### Removing a follow-up flag: `unflag` {#removing-a-follow-up-flag}

Removes an existing follow-up flag from a contact.

Format: `unflag INDEX`

Examples:
* `unflag 3`

### Undoing the previous change: `undo`

Reverts the most recent command that modified the address book.

Format: `undo`

Examples:
* `undo`

### Redoing the previous undo: `redo` {#redoing-the-previous-undo}

Restores the most recently undone command.

Format: `redo`

Examples:
* `redo`

### Deleting contact(s) : `delete`

Deletes one or more specified contacts from the address book.

Format: `delete INDEX [MORE_INDICES]`

Alternative formats:
* `delete START_INDEX-END_INDEX`
* `delete all`

* Deletes the contact(s) at the specified index or indices.
* The indices refer to the index numbers shown in the displayed contact list.
* Each index **must be a positive integer** 1, 2, 3, …​
* `delete 1 3 5` deletes multiple displayed contacts in one command.
* `delete 2-5` deletes a range of displayed contacts.
* `delete all` deletes all contacts currently shown in the displayed list.

Examples:
* `list` followed by `delete 2` deletes the 2nd contact in the address book.
* `list` followed by `delete 1 3 5` deletes the 1st, 3rd, and 5th contacts in the address book.
* `list` followed by `delete 2-4` deletes the 2nd to 4th contacts in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st contact in the results of the `find` command.
* `filter c/3A` followed by `delete all` deletes all currently displayed contacts in class `3A`.

### Clearing all entries : `clear` {#clearing-all-entries}

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit` {#exiting-the-program}

Exits the program.

Format: `exit`

### Saving the data {#saving-the-data}

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file {#editing-the-data-file}

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">⚠️ **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ {#faq}

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues {#known-issues}

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **During CSV import, header detection can be over-aggressive**. If the first row begins with `name`, the app may assume it is a header row and skip it without showing a row-level skip reason.

--------------------------------------------------------------------------------------------------------------------

## Command summary {#command-summary}

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [c/CLASS] [r/REMARK] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 c/3A t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX [MORE_INDICES]`<br> e.g., `delete 3`, `delete 1 3 5`, `delete all`
**Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/CLASS] [r/REMARK] [t/TAG]…​`<br> e.g., `edit 2 n/James Lee e/jameslee@example.com`
**Dashboard** | `dashboard`
**Flag** | `flag INDEX r/REASON`<br> e.g., `flag 3 r/Missing consent form`
**Unflag** | `unflag INDEX`<br> e.g., `unflag 3`
**Filter** | `filter c/CLASS`<br> e.g., `filter c/3A`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Import** | `import FILE_PATH`<br> e.g., `import C:\data\contacts.csv`
**Export** | `export FILE_PATH`<br> e.g., `export C:\data\contacts.csv`
**Remark** | `remark INDEX r/[REMARK]`<br> e.g., `remark 1 r/Allergic to peanuts`
**Tag** | `tag INDEX t/TAG [t/MORE_TAGS]`<br> e.g., `tag 1 t/exco`
**List** | `list`
**Sort** | `sort [address\|name]`<br> e.g., `sort`, `sort name`
**Undo** | `undo`
**Redo** | `redo`
**Help** | `help`
