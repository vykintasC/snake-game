import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Overridden start method.
     *
     * @param primaryStage new stage.
     * @throws Exception if fxml doesn't exist.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Super Snake");
        primaryStage.getIcons().add(new Image("file:snake.jpeg"));
        primaryStage.show();
    }

    /**
     * Main method.
     *
     * @param args launch.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
