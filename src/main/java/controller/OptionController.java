package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class OptionController extends AbstractController implements Initializable {

    @FXML
    AnchorPane anchorPaneMenuScreen;

    @FXML
    Button goBack;

    protected static String difficulty = "";

    protected static String walltype = "";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().setAll("Easy", "Medium", "Hard", "Extra Hard", "Extreme", "Legendary");
        comboBox.setPromptText("Difficulty");
        comboBox.setPrefWidth(150);
        ((VBox) anchorPaneMenuScreen.getChildren().get(2)).getChildren().set(1, comboBox);
        comboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(comboBox.getItems().get(1))) {
                        difficulty = "Medium";
                    } else if (newValue.equals(comboBox.getItems().get(2))) {
                        difficulty = "Hard";
                    } else if (newValue.equals(comboBox.getItems().get(3))) {
                        difficulty = "Extra Hard";
                    } else if (newValue.equals(comboBox.getItems().get(4))) {
                        difficulty = "Extreme";
                    } else if (newValue.equals(comboBox.getItems().get(5))) {
                        difficulty = "Legendary";
                    } else {
                        difficulty = "Easy";
                    }
                });
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().setAll("Walls", "No Walls");
        comboBox2.setPromptText("Wall Option");
        comboBox2.setPrefWidth(150);
        ((VBox) anchorPaneMenuScreen.getChildren().get(2)).getChildren().set(3, comboBox2);
        comboBox2.valueProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(comboBox2.getItems().get(0))) {
                        walltype = "Walls";
                    } else if (newValue.equals(comboBox2.getItems().get(1))) {
                        walltype = "No Walls";
                    } else {
                        walltype = "Walls";
                    }
                });
    }

    /**
     * Go back to main screen.
     *
     * @throws Exception if fxml is not there.
     */
    @FXML
    public void goBackToPrev() throws Exception {
        player.stop();
        loadScreen("/menuscreen.fxml", goBack);
    }

    /**
     * Getter for difficulty.
     */
    public static String getDifficulty() {
        return difficulty;
    }

    /**
     * Getter for wall type.
     */
    public static String getWalltype() {
        return walltype;
    }

    /**
     * Setter for difficulty.
     */
    public static void setDifficulty(String difficulty) {
        OptionController.difficulty = difficulty;
    }

    /**
     * Setter for wall type.
     */
    public static void setWalltype(String walltype) {
        OptionController.walltype = walltype;
    }
}
