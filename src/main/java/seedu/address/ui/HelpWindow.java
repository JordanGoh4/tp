package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.HelpCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103-f09-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TableView<CommandEntry> commandTable;

    @FXML
    private TableColumn<CommandEntry, String> commandColumn;

    @FXML
    private TableColumn<CommandEntry, String> usageColumn;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                hide();
            }
        });

        initTable();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initialises the command table with data from {@code HelpCommand.COMMAND_USAGES}.
     */
    private void initTable() {
        commandColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().command));
        usageColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().description));

        // Bold the command column
        commandColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-font-weight: bold;");
                }
            }
        });

        // Wrap text in description column
        usageColumn.setCellFactory(col -> new TableCell<>() {
            private final Text text = new Text();
            {
                text.wrappingWidthProperty().bind(col.widthProperty().subtract(10));
                text.getStyleClass().add("cell-text");
                setGraphic(text);
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    text.setText(null);
                } else {
                    text.setText(item);
                }
            }
        });

        HelpCommand.COMMAND_USAGES.entrySet().stream()
                .sorted(Comparator.comparing(e -> getCommandOrder(e.getKey())))
                .map(e -> new CommandEntry(e.getKey(), stripCommandPrefix(e.getKey(), e.getValue())))
                .forEach(entry -> commandTable.getItems().add(entry));
    }

    /**
     * Strips the leading "commandWord: " prefix from a MESSAGE_USAGE string,
     * since the command word is already shown in its own column.
     */
    private static String stripCommandPrefix(String command, String usage) {
        String prefix = command + ": ";
        if (usage.startsWith(prefix)) {
            return usage.substring(prefix.length());
        }
        return usage;
    }

    /**
     * Returns an ordering index for the given command word so that related
     * commands are grouped together in the table.
     */
    private static int getCommandOrder(String command) {
        switch (command) {
        case "add": return 0;
        case "edit": return 1;
        case "delete": return 2;
        case "remark": return 3;
        case "tag": return 4;
        case "flag": return 5;
        case "unflag": return 6;
        case "find": return 7;
        case "filter": return 8;
        case "list": return 9;
        case "sort": return 10;
        case "dashboard": return 11;
        case "import": return 12;
        case "export": return 13;
        case "undo": return 14;
        case "redo": return 15;
        case "clear": return 16;
        case "help": return 17;
        case "exit": return 18;
        default: return 99;
        }
    }

    /**
     * Shows the help window.
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Replaces the help window message contents with the given text.
     */
    public void setMessage(String message) {
        helpMessage.setText(message);
    }

    /**
     * Updates the help message and shows the help window.
     */
    public void showWithMessage(String message) {
        show();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Simple data holder for a row in the command table.
     */
    public static class CommandEntry {
        private final String command;
        private final String description;

        /**
         * Creates a CommandEntry with the given command word and description.
         */
        public CommandEntry(String command, String description) {
            this.command = command;
            this.description = description;
        }
    }
}
