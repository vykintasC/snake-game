package controller;

import database.Users;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.SoundTrack;


public abstract class AbstractController {

    protected static Users user;

    protected static SoundTrack player;

    /**
     * Helper method to load different screens.
     *
     * @param url of the fxml file.
     * @param btn to close the window.
     * @throws Exception if fxml doesn't exist.
     */
    public boolean loadScreen(String url, Button btn) throws Exception {
        closeScreen(btn);
        Stage st = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(url));
        st.setScene(new Scene(root, 600, 400));
        st.setTitle("Super Snake");
        st.getIcons().add(new Image("file:snake.jpeg"));
        st.show();
        return true;
    }


    /**
     * Helper method to close the current window.
     *
     * @param btn which decides the window.
     */
    public void closeScreen(Button btn) {
        Stage s = (Stage) btn.getScene().getWindow();
        s.close();
    }

    /**
     * The method to keep the details of the user who is playing.
     *
     * @param usr the user we get after login.
     */
    public void setUser(Users usr) {
        this.user = usr;
    }

    /**
     * To retrieve user later for saving scores.
     *
     * @return player.
     */
    public Users getUser() {
        return this.user;
    }

    /**
     * To retrieve the given soundtrack.
     */
    public static SoundTrack getPlayer() {
        return player;
    }
}
