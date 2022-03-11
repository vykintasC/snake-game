import controller.OptionController;

import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.Board;
import logic.Food;
import logic.Score;
import logic.Snake;
import logic.SnakeTail;
import logic.Tail;
import logic.UserPreferences;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SnakeTest {

    private static Snake snake;
    private static Food food;
    private static Board board;
    private String south = "South";
    private String west = "West";
    private String east = "East";
    private String north = "North";

    @BeforeEach
    void setUp() {
        board = new Board("user");
        snake = new Snake();
        snake.createSnake(600, 300, 20);
        food = new Food();
        food.createFood(400, 400, 20);
    }

    @Test
    void getDirection() {
        Assertions.assertEquals(snake.getDirection(), west);
    }

    @Test
    void setDirection() {
        snake.setDirection(east);
        Assertions.assertEquals(snake.getDirection(), east);
    }

    @Test
    void testCreateSnake() {
        Node img = snake.getNode();
        Assertions.assertEquals(300, img.getLayoutX());
        Assertions.assertEquals(150, img.getLayoutY());
        UserPreferences.type = "Default";
        snake.createSnake(600, 300, 20);
        Assertions.assertEquals(300, img.getLayoutX());
        Assertions.assertEquals(150, img.getLayoutY());
    }

    @Test
    void testTimer() {
        Score score = new Score();
        board.setScore(score);
        Pane pane = new Pane();
        snake.startTimer(food, pane, board);
        Timeline timer = snake.getTimeline();
        Assertions.assertEquals(1, timer.getRate());
        Assertions.assertTrue(timer.getCycleCount() == Timeline.INDEFINITE);
        Assertions.assertTrue(timer.getKeyFrames().get(0).getTime().equals(Duration.seconds(0.4)));
    }

    @Test
    void testPauseRunTimer() {
        Score score = new Score();
        board.setScore(score);
        Pane pane = new Pane();
        snake.startTimer(food, pane, board);
        Timeline timer = snake.getTimeline();
        Assertions.assertTrue(timer.getStatus().equals(Animation.Status.RUNNING));
        snake.pauseTimer();
        Assertions.assertTrue(snake.getTimeline().getStatus()
                .equals(Animation.Status.PAUSED));
        snake.resumeTimer();
        Assertions.assertTrue(snake.getTimeline().getStatus()
                .equals(Animation.Status.RUNNING));
    }

    @Test
    void testGameOver() {
        Assertions.assertThrows(NullPointerException.class, () -> snake.gameOver());
    }

    @Test
    void testSpeedUp() {
        Score score = new Score();
        board.setScore(score);
        Pane pane = new Pane();
        snake.startTimer(food, pane, board);
        OptionController.setDifficulty("Easy");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.1);
        snake.getTimeline().setRate(1);
        OptionController.setDifficulty("Legendary");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.3);
        snake.getTimeline().setRate(1);
        OptionController.setDifficulty("Extreme");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.2);
        snake.getTimeline().setRate(1);
        OptionController.setDifficulty("Extra Hard");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.1);
    }

    @Test
    void testSpeedUp2() {
        Score score = new Score();
        board.setScore(score);
        Pane pane = new Pane();
        snake.startTimer(food, pane, board);
        OptionController.setDifficulty("Hard");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.5);
        snake.getTimeline().setRate(1);
        OptionController.setDifficulty("Medium");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.3);
        snake.getTimeline().setRate(1);
        OptionController.setDifficulty("");
        snake.speedUp();
        Assertions.assertEquals(snake.getTimeline().getRate(), 1.1);
    }

    @Test
    void testMoveSnake() throws Exception {
        SnakeTail tails = new SnakeTail();
        Tail tail = new Tail(snake.getNode().getLayoutX() + 20,
                snake.getNode().getLayoutY());
        tail.showTail(20);
        tails.add(tail);
        snake.setSnakeTail(tails);
        //they have to be inversed
        double y = snake.getNode().getLayoutX();
        double x = snake.getNode().getLayoutY();
        snake.move(west, food);
        Assertions.assertEquals(y, snake.getPreviousX());
        Assertions.assertEquals(x, snake.getPreviousY());
        snake.move(north, food);
        snake.move(east, food);
        LinkedList<Point2D> list = snake.move(south, food);
        SnakeTail tails2 = snake.getTails();
        Assertions.assertEquals(list.size(), 5);
        Assertions.assertEquals(tails.getSize(), tails2.getSize());
    }

    @Test
    void testMoveSnake2() throws Exception {
        OptionController.setWalltype("Walls");
        testMoveSnake();
    }

    @Test
    void testMoveSnake3() {
        SnakeTail tails = new SnakeTail();
        Tail tail = new Tail(0, 0);
        tail.showTail(20);
        tails.add(tail);
        snake.setSnakeTail(tails);
        snake.getNode().setLayoutX(0);
        snake.getNode().setLayoutY(0);
        Assertions.assertEquals(0, snake.getPreviousX());
        Assertions.assertEquals(0, snake.getPreviousY());
        snake.setDirection(west);
        Assertions.assertTrue(snake.checkNoWallsX(20));
        snake.setDirection(north);
        Assertions.assertTrue(snake.checkNoWallsY(20));
    }

    @Test
    void testMoveSnake4() {
        SnakeTail tails = new SnakeTail();
        Tail tail = new Tail(Board.getWidth() - 20, Board.getHeight() - 20);
        tail.showTail(20);
        tails.add(tail);
        snake.setSnakeTail(tails);
        snake.getNode().setLayoutX(Board.getWidth() - 20);
        snake.getNode().setLayoutY(Board.getHeight() - 20);
        snake.setDirection(east);
        Assertions.assertTrue(snake.checkNoWallsX(20));
        snake.setDirection(south);
        Assertions.assertTrue(snake.checkNoWallsY(20));
    }

    @Test
    void testOnEat() {
        board.setScore(new Score());
        food.setPoints(1);
        snake.onEat(food);
        Assertions.assertEquals(1, board.getScore().getPoints());
    }

    @Test
    void testUpdateKeyStrokes() {
        String res = "west";
        snake.updateKeyStrokes("west");
        Assertions.assertEquals(res, snake.getDirection());
    }
}