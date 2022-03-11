package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class to store the preferences of the user, selected on the menu screen.
 * The colors are stored here when the user selected the option 'Rectangle'.
 * The type of node selected by the User is stored here. Can be 'Rectangle' or 'Image'.
 */
public class UserPreferences {
    public static Color colorhead = Color.BLACK;
    public static Color colortail = Color.BLACK;
    public static Color colorfood = Color.WHITE;
    public static Image imagehead = null;
    public static Image imagetail = null;
    public static Image imagefood = null;
    public static String type = "Rectangle";

    /**
     * Check the user preferences.
     *
     * @param colorPickerHead if head's color is selected.
     * @param colorPickerTail if tail's color is selected.
     * @param colorPickerFood if food's color is selected.
     * @return true if colors or images are selected.
     */
    public static boolean checkUserPreferences(ColorPicker colorPickerHead,
                                               ColorPicker colorPickerTail,
                                               ColorPicker colorPickerFood) {
        if (UserPreferences.type.equals("Rectangle")) {
            UserPreferences.colorhead = colorPickerHead.valueProperty().get();
            UserPreferences.colortail = colorPickerTail.valueProperty().get();
            UserPreferences.colorfood = colorPickerFood.valueProperty().get();
            return true;
        } else if (UserPreferences.type.equals("Default")) {
            try {
                UserPreferences.imagehead = new Image(new FileInputStream("default_head.png"));
                UserPreferences.imagetail = new Image(new FileInputStream("default_tail.jpg"));
                UserPreferences.imagefood = new Image(new FileInputStream("default_food.png"));
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (!UserPreferences.type.equals("Image")) {
            ErrorHandler errhan = new ErrorHandler("Error on input",
                    "Please select ",
                    "Please fill in the correct username and password",
                    Alert.AlertType.ERROR);
            errhan.throwMessage();
            return false;
        }
        return true;
    }

    /**
     * Method to create Images.
     * @param anchorPaneMenuScreen the display window.
     */
    public static void createImages(AnchorPane anchorPaneMenuScreen) {
        Stage stage = (Stage) anchorPaneMenuScreen.getScene().getWindow();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Images", "*.png", "*.jpg", "*.jpeg", "*.gif");
        File file1 = createImg("Select image for the head of the snake.",
                stage, fileExtensions);
        if (file1 != null) {
            UserPreferences.imagehead = new Image(file1.toURI().toString());
        }
        File file2 = createImg("Select image for the tail of the snake.",
                stage, fileExtensions);
        if (file2 != null) {
            UserPreferences.imagetail = new Image(file2.toURI().toString());
        }
        File file3 = createImg("Select image for the food.",
                stage, fileExtensions);
        if (file3 != null) {
            UserPreferences.imagefood = new Image(file3.toURI().toString());
        }
    }

    /**
     * Helper method to create Image.
     *
     * @param name name of the requirement.
     * @param stage on which the images will be shown.
     * @param fileExtensions formats of images.
     * @return File which is used as an image.
     */
    private static File createImg(String name, Stage stage,
                           FileChooser.ExtensionFilter fileExtensions) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(name);
        fileChooser.getExtensionFilters().add(fileExtensions);
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

}
