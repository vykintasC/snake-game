package logic;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Board extends Application {

    protected static int width = 600;
    protected static int height = 300;
    private static int heightTop = 50;
    protected static Button pausebutton;
    protected static boolean paused;
    protected static Score score;
    protected static BorderPane root;
    private String username;
    private static int ratio = 20;

    public Board(String username) {
        this.username = username;
    }

    /**
     * Create a basic board and render it with javafx.
     *
     * @param primaryStage Given the stage to initialize the board.
     */
    public void start(Stage primaryStage) {
        try {
            final Pane centrepane = new Pane();
            final Canvas c = new Canvas(width, height);
            GraphicsContext gc = c.getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);
            gc.setFill(Color.DARKGREY);
            gc.fillRect(0, 0, width, height);
            for (int i = 0; i < width / ratio; i++) {
                gc.strokeLine(i * ratio, 0, i * ratio, height);
            }
            for (int i = 0; i < height / ratio; i++) {
                gc.strokeLine(0, i * ratio, width, i * ratio);
            }
            score = new Score();

            //create snake's head
            Snake snake = new Snake();
            snake.createSnake(width, height + heightTop + 10, ratio);

            //Create 'food'
            Food food = new Food();
            food.createFood(width, height, ratio);

            Tail tail = new Tail(snake.getNode().getLayoutX() + 20,
                    snake.getNode().getLayoutY());
            tail.showTail(ratio);
            Tail tail1 = new Tail(snake.getNode().getLayoutX() + 40,
                    snake.getNode().getLayoutY());
            tail.showTail(ratio);
            Tail tail2 = new Tail(snake.getNode().getLayoutX() + 60,
                    snake.getNode().getLayoutY());
            tail.showTail(ratio);

            //Place 'food' and snake on the grid
            centrepane.getChildren().addAll(c, food.getImageView(),
                    snake.getNode(), tail.getNode(), tail1.getNode(), tail2.getNode());

            //Create the BorderPane, with the username, score and button at the top
            root = new BorderPane();
            root.setPrefSize(width, height + heightTop);
            root.setTop(addHBox());
            ((Label) ((HBox) root.getChildren().get(0)).getChildren().get(2))
                    .setText("Score: " + score.getPoints());
            root.setStyle("-fx-background-color: #a9d18e");
            root.setCenter(centrepane);

            //Listener for the Pause/Resume-button
            pausebutton.setOnAction(event -> {
                if (paused) {
                    snake.resumeTimer();
                    pausebutton.setText("Pause");
                } else {
                    snake.pauseSoundTrack();
                    snake.pauseTimer();
                    pausebutton.setText("Resume");
                }
                paused = !paused;
            });

            SnakeTail snakeTail = new SnakeTail();
            snakeTail.add(tail);
            snakeTail.add(tail1);
            snakeTail.add(tail2);

            //start moving the snake and check for collisions
            snake.setSnakeTail(snakeTail);
            snake.startTimer(food, centrepane, this);

            //check for changes in snake's direction
            Scene scene = new Scene(root, width, height + heightTop);
            scene.setOnKeyPressed(snake.changeDirection());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Super Snake");
            primaryStage.getIcons().add(new Image("file:snake.jpeg"));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to make the top of the screen that contains the username, button
     * to pause and resume the game, and the score.
     *
     * @return HBox that represents the top of the screen containing the username, button and score.
     */
    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(width / 5);
        hbox.setPrefSize(width, heightTop);

        Label username = new Label("Username: " + this.username);
        username.setMinSize(130, heightTop - 20);
        username.setAlignment(Pos.CENTER_LEFT);
        username.setFocusTraversable(false);
        username.setStyle("-fx-font-weight: bold");

        pausebutton = new Button("Pause");
        pausebutton.setMinSize(100, heightTop - 20);
        pausebutton.setAlignment(Pos.CENTER);
        pausebutton.setFocusTraversable(false);
        pausebutton.setStyle("    -fx-background-color: #759e59;\n"
                + "    -fx-border-width: 1px;\n"
                + "    -fx-border-radius: 5;\n"
                + "    -fx-border-color: black;\n"
                + "    -fx-background-radius: 5;\n"
                + "    -fx-background-insets: 0;\n"
                + "    -fx-font-size: 15px;");

        Label score = new Label("Score: ");
        score.setMinSize(110, heightTop - 20);
        score.setAlignment(Pos.CENTER_RIGHT);
        score.setFocusTraversable(false);
        score.setStyle("-fx-font-weight: bold");

        hbox.getChildren().addAll(username, pausebutton, score);

        return hbox;
    }

    /**
     * Getter for the Score.
     *
     * @return score.
     */
    public static Score getScore() {
        return score;
    }

    /**
     * Setter for score.
     *
     * @param score the score.
     */
    public void setScore(Score score) {
        this.score = score;
    }

    /**
     * Getter for the pausebutton.
     *
     * @return The pausebutton
     */
    public static Button getPausebutton() {
        return pausebutton;
    }

    /**
     * Getter for width.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Getter for height.
     */
    public static int getHeight() {
        return height;
    }

}
