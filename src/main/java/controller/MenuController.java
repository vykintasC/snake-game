package controller;

import database.ScoresDao;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Board;
import logic.SoundTrack;
import logic.UserPreferences;

@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class MenuController extends AbstractController implements Initializable {

    private Board board;

    @FXML
    private Button startGameButton;

    @FXML
    private Label record;

    protected static String fileName = "music.wav";

    @FXML
    VBox vboxColors;

    @FXML
    VBox vboxImages;

    @FXML
    ColorPicker colorPickerHead;

    @FXML
    ColorPicker colorPickerTail;

    @FXML
    ColorPicker colorPickerFood;

    @FXML
    AnchorPane anchorPaneMenuScreen;

    @FXML
    Button leaderboard;

    /**
     * Constructor.
     */
    public MenuController() {
    }

    /**
     * Method to initialize the ComboBox to select the layout.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            player = new SoundTrack();
            player.setFilePath(fileName);
            player.loopAudio();
            player.setVolume(0.7f);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScoresDao dao = new ScoresDao();
        record.setText(record.getText() + " "
                + dao.highScoreOfUser(super.getUser().getUsername()));

        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().setAll("Upload images", "Select colors", "Use default images");
        comboBox.setPromptText("Choose Layout");
        comboBox.setPrefWidth(150);
        ((VBox) anchorPaneMenuScreen.getChildren().get(2)).getChildren().set(2, comboBox);

        comboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(comboBox.getItems().get(0))) {
                        usersPictures();
                    } else if (newValue.equals(comboBox.getItems().get(1))) {
                        setVisible(true, false);
                        UserPreferences.type = "Rectangle";
                    } else if (newValue.equals(comboBox.getItems().get(2))) {
                        setVisible(false, false);
                        UserPreferences.type = "Default";
                    } else {
                        setVisible(false, false);
                        UserPreferences.type = "";
                    }
                });
    }

    /**
     * Helper extracted method to select user pictures.
     */
    public void usersPictures() {
        setVisible(false, true);
        UserPreferences.type = "Image";
        UserPreferences.createImages(anchorPaneMenuScreen);
    }

    /**
     * Helper extracted method.
     *
     * @param color vboxColor.
     * @param image vboxImage.
     */
    private void setVisible(boolean color, boolean image) {
        vboxColors.setVisible(color);
        vboxImages.setVisible(image);
    }

    /**
     * Method to initialize the board and start the game. This will be deleted later.
     */
    public void startGame() {
        if (UserPreferences.checkUserPreferences(colorPickerHead,
                colorPickerTail, colorPickerFood)) {
            closeScreen(startGameButton);
            this.board = new Board(getUser().getUsername());
            board.start(new Stage());
        } else {
            System.out.println("Could not start the game.");
            return;
        }
    }

    /**
     * Go to LeaderBoard screen.
     *
     * @throws Exception if screen not found.
     */
    public void showLeaders() throws Exception {
        loadScreen("/leaderboardscreen.fxml", startGameButton);
    }

    /**
     * Go to change password screen.
     *
     * @throws Exception if fxml is not there.
     */
    public void changePass() throws Exception {
        loadScreen("/changepassword.fxml", startGameButton);
    }

    /**
     * Go to other options screen.
     *
     * @throws Exception if fxml is not there.
     */
    public void otherOptions() throws Exception {
        loadScreen("/optionscreen.fxml", startGameButton);
    }
}
