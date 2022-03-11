package controller;

import database.UsersDao;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.ErrorHandler;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterController extends AbstractAccountController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button reg;

    @FXML
    private Button gomain;

    /**
     * Constructor.
     */
    public RegisterController() {

    }

    /**
     * In the register screen take username and password and register the user.
     *
     * @return true if registered, otherwise false.
     * @throws Exception if fxml doesn't exist.
     */
    @Override
    @FXML
    public boolean register() throws Exception {
        String name = username.getText();
        String pass = password.getText();
        if (name.isEmpty() || pass.isEmpty()) {
            ErrorHandler errorHandler = new ErrorHandler("Error on input",
                    "No username or password detected",
                    "Please fill in both the username and the password",
                    Alert.AlertType.ERROR);
            errorHandler.throwMessage();
            return false;
        }   else {
            UsersDao user = new UsersDao();
            String hashedPass = BCrypt.hashpw(pass, BCrypt.gensalt(12));

            if (user.insertUser(name, hashedPass)) {
                loadScreen("/login.fxml", reg);
                return true;
            } else {
                ErrorHandler errorHandler = new ErrorHandler("Error on input",
                        "Username already exists",
                        "Please fill in another username",
                        Alert.AlertType.ERROR);
                errorHandler.throwMessage();
                return false;
            }
        }
    }

    /**
     * Go back to main screen.
     *
     * @throws Exception if fxml doesn't exist.
     */
    @FXML
    private void goBack() throws Exception {
        loadScreen("/login.fxml", gomain);
    }
}
