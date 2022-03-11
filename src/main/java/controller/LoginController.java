package controller;

import database.Users;
import database.UsersDao;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.ErrorHandler;

public class LoginController extends AbstractAccountController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button reg;

    /**
     * Constructor.
     */
    public LoginController() {
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
        } else {
            UsersDao user = new UsersDao();
            Users player = user.login(name, pass);
            if (player != null) {
                setUser(player);
                loadScreen("/menuscreen.fxml", login);
                return true;
            } else {
                ErrorHandler errorHandler = new ErrorHandler("Error on input",
                        "Username or password incorrect",
                        "Please fill in the correct username and password",
                        Alert.AlertType.ERROR);
                errorHandler.throwMessage();
                return false;
            }
        }
    }

    /**
     * Go to register screen for a new user.
     *
     * @return true if register.fxml exists, otherwise false.
     */
    @FXML
    public boolean goLogin() {
        try {
            loadScreen("/register.fxml", reg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
