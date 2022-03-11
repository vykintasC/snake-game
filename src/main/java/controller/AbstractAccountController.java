package controller;

public abstract class AbstractAccountController extends AbstractController {

    /**
     * Constructor for the AccountController.
     */
    public AbstractAccountController() {
    }

    /**
     * Abstract register method which gets implemented differently in
     * both RegisterController and LoginController.
     */
    public abstract boolean register() throws Exception;
}
