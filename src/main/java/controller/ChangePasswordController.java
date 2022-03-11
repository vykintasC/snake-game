package controller;

import database.UsersDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.mindrot.jbcrypt.BCrypt;

public class ChangePasswordController extends AbstractController {

    @FXML
    PasswordField password;

    @FXML
    Button goBack;

    @FXML
    Label notification;

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
     * Users can change their passwords here.
     */
    @FXML
    public void changePass() {
        if (!password.getText().isEmpty()) {
            notification.setText("Password changed.");
            String hashedPass = BCrypt.hashpw(password.getText(), BCrypt.gensalt(12));
            UsersDao dao = new UsersDao();
            dao.changePassword(super.getUser().getUsername(), hashedPass);
            notification.setVisible(true);
            password.clear();
        } else {
            notification.setText("Fill in the password.");
            notification.setVisible(true);
        }
    }
}
