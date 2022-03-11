package logic;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class to throw an error with a specific message.
 */
public class ErrorHandler {
    private String title;
    private String header;
    private String content;
    private Alert.AlertType type;

    /**
     * Constructor to make an ErrorHandler that makes it possible
     * to throw an error with a specific message.
     *
     * @param title   the title of the error message
     * @param header  the header of the error message
     * @param content the content of the error message
     */
    public ErrorHandler(String title, String header, String content, Alert.AlertType type) {
        this.title = title;
        this.header = header;
        this.content = content;
        this.type = type;
    }

    /**
     * Method that throws an error with the given information.
     * This is done when the username or password (or both) are not
     * filled in, or when the combination of username and password
     * is incorrect.
     */
    public void throwMessage() {
        Alert alert = new Alert(type);
        alert.setTitle(this.title);
        alert.setHeaderText(this.header);
        alert.setContentText(this.content);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:snake.jpeg"));

        alert.showAndWait();
    }
}
