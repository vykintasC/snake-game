import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import logic.Food;
import logic.Snake;
import logic.SnakeTail;
import logic.Tail;
import logic.UserPreferences;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FoodTest {

    private Food food;
    private int width = 400;
    private int height = 400;
    private int ratio = 20;

    @BeforeEach
    void setUp() {
        food = new Food();
        food.createFood(width, height, ratio);
    }

    @Test
    void testConstructor() {
        Assertions.assertTrue(food.getPoints() == 1);
    }

    @Test
    void testRandomiseCoordinates() {
        food.createFood(400, 400, 20);
        Point2D oldPoint = food.getPosition();
        food.randomizeCoordinates(null);
        Point2D newPoint = food.getPosition();

        Assertions.assertTrue(oldPoint.getX() != newPoint.getX()
                || oldPoint.getY() != newPoint.getX());
    }

    @Test
    void testCreateFood() throws FileNotFoundException {
        UserPreferences.type = "Default";
        UserPreferences.imagefood = new Image(new
                FileInputStream("default_food.png"));
        boolean created = food.createFood(20, 20, 20);
        Assertions.assertTrue(created);
    }

    @Test
    void testGetImgView() {
        Food food1 = new Food();
        Assertions.assertNull(food1.getImageView());
        Assertions.assertTrue(food.createFood(400, 400, 20));
    }

    @Test
    void testGetPosition() {
        food.createFood(400, 400, 20);
        Point2D point = food.getPosition();

        Assertions.assertTrue(point != null);
        Assertions.assertTrue(point.getX() < 400);
        Assertions.assertTrue(point.getY() < 400);
        Assertions.assertTrue(point.getX() % 20 == 0);
        Assertions.assertTrue(point.getY() % 20 == 0);
    }

    @Test
    void testIsEaten() throws FileNotFoundException {
        food.createFood(400, 400, 20);
        Point2D oldPoint = food.getPosition();

        Snake snake = new Snake();
        snake.createSnake(400, 400, 20);
        snake.getNode().setLayoutX(oldPoint.getX());
        snake.getNode().setLayoutY(oldPoint.getY());

        boolean isEaten = food.isEaten(snake);

        Assertions.assertTrue(isEaten);
    }

    @Test
    void testTimer() {
        food.createFood(400, 400, 20);

        final Point2D oldPoint = food.getPosition();

        food.startTimer();
        try {
            Thread.sleep(110);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        food.stopTimer();

        Point2D newPoint = food.getPosition();

        Assertions.assertTrue(oldPoint.getX() != newPoint.getX()
                || oldPoint.getY() != newPoint.getY());
    }

    @Test
    void testIsNotEaten() throws FileNotFoundException {
        food.createFood(400, 400, 20);
        Point2D oldPoint = food.getPosition();

        Snake snake = new Snake();
        snake.createSnake(400, 400, 20);
        snake.getNode().setLayoutX(oldPoint.getX() + 20);
        snake.getNode().setLayoutY(oldPoint.getY() + 20);

        boolean isEaten = food.isEaten(snake);
        Assertions.assertFalse(isEaten);
        snake.getNode().setLayoutX(oldPoint.getX());
        boolean isEaten2 = food.isEaten(snake);
        Assertions.assertFalse(isEaten2);
        snake.getNode().setLayoutX(oldPoint.getX() + 20);
        snake.getNode().setLayoutY(oldPoint.getY());
        boolean isEaten3 = food.isEaten(snake);
        Assertions.assertFalse(isEaten3);

    }

    //test 200 random food respawns to see if they respawn inside the snake.
    @Test
    void testWithSnake() throws FileNotFoundException {
        Snake snake = new Snake();
        snake.createSnake(width, height, ratio);
        SnakeTail snakeTail = new SnakeTail();
        Tail tail1 = new Tail(snake.getNode().getLayoutX()
                + ratio, snake.getNode().getLayoutY());
        Tail tail2 = new Tail(snake.getNode().getLayoutX()
                + 2 * ratio, snake.getNode().getLayoutY());
        Tail tail3 = new Tail(snake.getNode().getLayoutX()
                + 3 * ratio, snake.getNode().getLayoutY());
        snakeTail.add(tail1);
        snakeTail.add(tail2);
        snakeTail.add(tail3);
        tail1.showTail(ratio);
        tail2.showTail(ratio);
        tail3.showTail(ratio);
        snake.setSnakeTail(snakeTail);

        food.createFood(width, height, ratio);
        Set<Point2D> set = new HashSet<>();
        for (int i = 0; i < snake.getTails().getTail().size(); i++) {
            Tail tail = snake.getTails().getTail(i);
            set.add(new Point2D(tail.getNode().getLayoutX(), tail.getNode().getLayoutY()));
        }
        set.add(new Point2D(snake.getNode().getLayoutX(),
                snake.getNode().getLayoutY()));

        List<Point2D> newFoodCoords = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            newFoodCoords.add(food.randomizeCoordinates(snake));
        }
        Set<Point2D> foodSet = new HashSet<>(newFoodCoords);
        foodSet.retainAll(set);
        Set<Point2D> empty = new HashSet<>();
        Assertions.assertSame(empty.isEmpty(), foodSet.isEmpty());
    }
}
