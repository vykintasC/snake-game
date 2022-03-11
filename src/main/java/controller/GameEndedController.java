package controller;

import database.ScoresDao;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.Board;
import logic.ErrorHandler;


public class GameEndedController extends AbstractController implements Initializable {

    @FXML
    private Button savescoreButton;

    @FXML
    private TextField gameName;

    @FXML
    private Label displayScore;

    static String fileName = "GameOver.wav";

    /**
     * Constructor.
     */
    public GameEndedController() {
    }

    /**
     * Helper method to load the scene where the score can be saved.
     *
     * @throws Exception if fxml doesn't exist.
     */
    @FXML
    public void loadScreen() throws Exception {
        loadScreen("/gameEnded.fxml", Board.getPausebutton());
        try {
            player.stop();
            player.setFilePath(fileName);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * After unsuccessful try, open a new window.
     *
     * @throws Exception if fxml doesn't exist.
     */
    @FXML
    private void saveScore() throws Exception {
        String scorename = gameName.getText();
        if (scorename.isEmpty()) {
            ErrorHandler errorHandler = new ErrorHandler("No input detected",
                    "Score could not be saved",
                    "No input detected to name your score",
                    Alert.AlertType.WARNING);
            errorHandler.throwMessage();
        }
        ScoresDao dao = new ScoresDao();
        dao.insertScore(super.getUser().getUser_ID(), Board.getScore().getPoints(),
                gameName.getText());
        player.stop();
        loadScreen("/menuscreen.fxml", savescoreButton);
    }

    /**
     * To set the score on the screen.
     *
     * @param location  have no clue.
     * @param resources as well.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayScore.setText("Score: " + Board.getScore().getPoints());
    }
}
