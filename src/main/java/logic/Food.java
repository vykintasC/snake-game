package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Food {

    private Timeline fiveSecondsWonder;
    private Point2D point;

    private static int gridx;
    private static int gridy;
    private static int gridratio;

    private static double seconds = 0.1;
    private int points;
    private Node node;

    /**
     * A constructor for food. For every eaten food a player gets 1 point.
     */
    public Food() {
        this.points = 1;
    }

    /**
     * Getter to return image view.
     *
     * @return imageView of the apple image.
     */
    public Node getImageView() {
        return node;
    }

    /**
     * Getter to return how many points a food is worth.
     *
     * @return amount of points.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Get the current 2D food position.
     *
     * @return point where the 'food' is located at.
     */
    public Point2D getPosition() {
        return point;
    }

    /**
     * Function creates a food with a picture.
     *
     * @param x     size of the grid in x axis.
     * @param y     size of the grid in y axis.
     * @param ratio spacing of the grid squares
     */
    public boolean createFood(int x, int y, int ratio) {
        if (UserPreferences.type.equals("Rectangle")) {
            node = new Rectangle(0, 0, ratio, ratio);
            ((Rectangle) node).setFill(UserPreferences.colorfood);
        } else if (UserPreferences.type.equals("Default") || UserPreferences.type.equals("Image")) {
            node = new ImageView(UserPreferences.imagefood);
            ((ImageView) node).setFitHeight(ratio);
            ((ImageView) node).setFitWidth(ratio);
        }

        gridx = x;
        gridy = y;
        gridratio = ratio;
        this.point = new Point2D(x / 2 - x / 2 % ratio, y / 2 - y / 2 % ratio);
        node.setLayoutX(point.getX());
        node.setLayoutY(point.getY());

        return true;
    }

    /**
     * After the food is eaten, it must be placed in another random position.
     */
    public Point2D randomizeCoordinates(Snake snake) {
        if (snake == null) {
            Point2D point;
            //idk what to do if there is only a 1by1 board,
            //but this is just here so that the code works without having a snake.
            do {
                point = new Point2D(randomX(), randomY());
            } while (point.equals(this.point));
            this.point = point;
            return point;
        }
        //create a set that contains all points on the board
        Set<Point2D> set = new HashSet<>();
        for (int i = 0; i < (gridx / gridratio); i++) {
            for (int j = 0; j < (gridy / gridratio); j++) {
                set.add(new Point2D(i * gridratio, j * gridratio));
            }
        }

        //create a set that contains all points where a snake piece is.
        Set<Point2D> snakeSet = new HashSet<>();
        // add tail pieces
        if (snake.getTails() != null
                && snake.getTails().getTail() != null) {
            //for(Tail tail: snake.getTails().getTail) didn't work due to a bug in spot bugs
            //well it worked but gave errors in spot bugs, even though the code was correct.
            for (int i = 0; i < snake.getTails().getTail().size(); i++) {
                Tail tail = snake.getTails().getTail(i);
                snakeSet.add(new Point2D(tail.getNode().getLayoutX(),
                        tail.getNode().getLayoutY()));
            }
        }
        //add head.
        snakeSet.add(new Point2D(snake.getNode().getLayoutX(),
                snake.getNode().getLayoutY()));

        //from the set with all points, remove all points where a snake is.
        set.removeAll(snakeSet);

        //get a random index
        int index = (int) (Math.random() * set.size());

        //and assign the new point to the image and to the point variable in food.
        List<Point2D> indexList = new ArrayList<>(set);
        node.setLayoutX(indexList.get(index).getX());
        node.setLayoutY(indexList.get(index).getY());
        this.point = indexList.get(index);

        //return for testability
        return this.point;
    }

    /**
     * Make a random x coordinate.
     *
     * @return random x position on the grid.
     */
    private int randomX() {
        int xcoord = (int) (Math.random() * gridx) / gridratio * gridratio;
        return xcoord;
    }

    /**
     * Make a random y coordinate.
     *
     * @return random y position on the grid.
     */
    private int randomY() {
        int ycoord = (int) (Math.random() * gridy) / gridratio * gridratio;
        return ycoord;
    }

    /**
     * Timer to change the position of the food during more difficult levels of the game.
     */
    public void startTimer() {
        randomizeCoordinates(null);
        EventHandler<ActionEvent> event = moveFood();
        fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(seconds), event));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    /**
     * Stops the timer and randomization of food coordinates.
     */
    public void stopTimer() {
        fiveSecondsWonder.stop();
    }


    /**
     * To check weather the food is eaten by the snake.
     *
     * @param snake the snake which is on the board
     * @return true if snake's head coordinates are the same as the food's coordinates
     */
    public boolean isEaten(Snake snake) {
        if (snake.getNode()
                .getLayoutX() == point.getX()
                && snake.getNode().getLayoutY() == point.getY()) {
            return true;
        }
        return false;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Move the food to a random place (with timer).
     *
     * @return EventHandler which can then be added to the scene.
     */
    public EventHandler<ActionEvent> moveFood() {
        randomizeCoordinates(null);
        return event1 -> System.out.println("remove the comment in moveFood");
    }
}
