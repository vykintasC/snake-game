package controller;

import database.ScoresDao;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class LeaderBoardController extends AbstractController implements Initializable {

    @FXML
    Label highscores;

    @FXML
    Button goBack;

    /**
     * Initialize method.
     *
     * @param location idk.
     * @param resources idk.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScoresDao dao = new ScoresDao();
        Map<String, Integer> scores = dao.retrieveGameNameHighScores();
        String result = "Top 5 scores: \n\n";
        for (Map.Entry mapEntry : scores.entrySet()) {
            result += mapEntry.getKey() + " : " + mapEntry.getValue() + "\n";
        }
        highscores.setText(result);
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
}
