package logic;

import controller.AbstractController;
import controller.GameEndedController;
import controller.OptionController;

import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


/**
 * Class to make a Snake, pointing a in specific direction.
 * The coordinates of the Snake are stored in a LinkedList.
 */
public class Snake {

    private int keyStrokes;
    private double previousX;
    private double previousY;
    private Node node;
    private LinkedList<Point2D> coordinates;
    private String direction;
    private boolean grow;
    private boolean update;
    private boolean canChangeDirection = true;
    private final String north = "North";
    private final String south = "South";
    private final String east = "East";
    private final String west = "West";
    private Timeline fiveSecondsWonder;
    private SnakeTail tail;

    /**
     * Initialize the Snake with its starting position.
     * The Snake has a length of three, it only contains three square.
     * Make the snake point in the North-direction initially.
     */
    public Snake() {
        this.direction = west;
        coordinates = new LinkedList<>();
        grow = false;
        update = true;
    }

    public double getPreviousX() {
        return this.previousX;
    }

    public double getPreviousY() {
        return this.previousY;
    }

    public void setSnakeTail(SnakeTail tail) {
        this.tail = tail;
    }

    /**
     * Function to place snake on the grid with appropriate sizing.
     *
     * @param x     grid width.
     * @param y     grid height.
     * @param ratio the size of the square.
     */
    public void createSnake(int x, int y, int ratio) {
        if (UserPreferences.type.equals("Rectangle")) {
            node = new Rectangle(0, 0, ratio, ratio);
            ((Rectangle) node).setFill(UserPreferences.colorhead);
        } else if (UserPreferences.type.equals("Default") || UserPreferences.type.equals("Image")) {
            node = new ImageView(UserPreferences.imagehead);
            ((ImageView) node).setFitHeight(ratio);
            ((ImageView) node).setFitWidth(ratio);
        }

        int centerX = x / 2;
        int centerY = y / 2;
        node.setLayoutY(centerY);
        node.setLayoutX(centerX);
        coordinates.add(new Point2D(centerX, centerY));
    }

    /**
     * Get Node.
     *
     * @return image view of the snake picture.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Coordinates of the snake.
     *
     * @return list of coordinates of the snake.
     */
    public SnakeTail getTails() {
        return tail;
    }

    /**
     * Get direction of the snake.
     *
     * @return the current direction of snake.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Get the timer.
     *
     * @return timer.
     */
    public Timeline getTimeline() {
        return fiveSecondsWonder;
    }

    /**
     * Set direction of the snake.
     *
     * @param direction one of the options West, North, East, South.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Start moving the snake.
     *
     * @param food to check for collisions.
     */
    public void startTimer(Food food, Pane root, Board board) {
        EventHandler<ActionEvent> event = moveSnake(food, root);
        fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.4), event));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        fiveSecondsWonder.setRate(1);
    }

    /**
     * Pause the game and stop moving the snake.
     */
    public void pauseTimer() {
        fiveSecondsWonder.pause();
    }

    /**
     * Pause the current soundtrack.
     */
    public void pauseSoundTrack() {
        AbstractController.getPlayer().pause();
    }

    /**
     * Increase the speed of the snake when we get more points.
     */
    public void speedUp() {
        switch (OptionController.getDifficulty()) {
            case "Legendary":
                fiveSecondsWonder.setRate(1.30 * fiveSecondsWonder.getRate());
                break;
            case "Extreme":
                fiveSecondsWonder.setRate(1.20 * fiveSecondsWonder.getRate());
                break;
            case "Extra Hard":
                fiveSecondsWonder.setRate(1.10 * fiveSecondsWonder.getRate());
                break;
            case "Hard":
                fiveSecondsWonder.setRate(0.50 + fiveSecondsWonder.getRate());
                break;
            case "Medium":
                fiveSecondsWonder.setRate(0.30 + fiveSecondsWonder.getRate());
                break;

            default:
                fiveSecondsWonder.setRate(0.10 + fiveSecondsWonder.getRate());
                break;
        }
    }

    /**
     * Resume the game and start moving the snake again.
     */
    public void resumeTimer() {
        try {
            AbstractController.getPlayer().resumeAudio("music.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        fiveSecondsWonder.play();
    }

    /**
     * Move the snake in the direction changed.
     *
     * @param food to check for collisions.
     * @return the event handler for this event.
     */
    private EventHandler<ActionEvent> moveSnake(Food food, Pane root) {
        return event1 -> {
            try {
                move(getDirection(), food);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tail.update(this, grow, root);
            if (grow == true) {
                ((Label) ((HBox) Board.root.getChildren().get(0)).getChildren().get(2))
                        .setText("Score: " + Board.score.getPoints());
            }
            // sets grown to false so the next time it is used we dont accidentally grow.
            grow = false;
            keyStrokes = 0;
        };
    }

    /**
     * Method to move the Snake, using the direction of the Snake.
     *
     * @return The LinkedList that contains the new Points of the Snake.
     */
    public LinkedList<Point2D> move(String direction, Food food) throws Exception {
        this.previousX = node.getLayoutX();
        this.previousY = node.getLayoutY();

        switch (direction) {
            case north:
                makeMoves(node.getLayoutY(), -20, node.getLayoutX(), 0);
                break;
            case east:
                makeMoves(node.getLayoutY(), 0, node.getLayoutX(), 20);
                break;
            case south:
                makeMoves(node.getLayoutY(), 20, node.getLayoutX(), 0);
                break;
            case west:
                makeMoves(node.getLayoutY(), 0, node.getLayoutX(), -20);
                break;
            default:
                break;
        }
        if (food.isEaten(this)) {
            onEat(food);
        }
        return coordinates;

    }

    /**
     * Opens the window to store the score when the snake died.
     */
    public void openWindowSnakeDied() throws Exception {
        GameEndedController gameEndedController = new GameEndedController();
        gameEndedController.loadScreen();
    }


    /**
     * handles everything when we eat some food. (points, growing, updating labels)
     *
     * @param food the type of food the snake eats.
     */
    public void onEat(Food food) {
        Board.score.increaseScore(food);
        food.randomizeCoordinates(this);
        grow = true;
        if ((Board.score.getPoints() % 3) == 0) {
            speedUp();
        }
        try {
            SoundTrack soundTrack = new SoundTrack();
            soundTrack.setFilePath("Apple Crunch.wav");
            soundTrack.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Listener to change snakes position.
     *
     * @return event with the changed direction.
     */
    public EventHandler<KeyEvent> changeDirection() {
        return event1 -> {
            KeyCode code = event1.getCode();
            if (code == KeyCode.SPACE) {
                if (Board.paused) {
                    resumeTimer();
                    Board.pausebutton.setText("Pause");
                } else {
                    pauseTimer();
                    pauseSoundTrack();
                    Board.pausebutton.setText("Resume");
                }
                Board.paused = !Board.paused;
            } else if (canChangeDirection && keyStrokes == 0) {
                if (code == KeyCode.RIGHT && !direction.equals(west)) {
                    updateKeyStrokes(east);
                } else if (code == KeyCode.LEFT && !direction.equals(east)) {
                    updateKeyStrokes(west);
                } else if (code == KeyCode.UP && !direction.equals(south)) {
                    updateKeyStrokes(north);
                } else if (code == KeyCode.DOWN && !direction.equals(north)) {
                    updateKeyStrokes(south);
                }
            }
        };
    }


    /**
     * Update the current keyStrokes.
     */
    public void updateKeyStrokes(String direction) {
        canChangeDirection = false;
        setDirection(direction);
        canChangeDirection = true;
        keyStrokes++;
    }



    /**
     * This method is a helper method for move(), otherwise move is very long.
     *
     * @param imgY y coordinate of snake.
     * @param addY what to add to that coordinate.
     * @param imgX x coordinate of snake.
     * @param addX what to add to that coordinate.
     * @return true if move is made.
     * @throws Exception if snake img doesn't exist.
     */
    public boolean makeMoves(double imgY, double addY, double imgX, double addX) throws Exception {
        for (int i = 0; i < tail.getTail().size() - 1; ++i) {
            Tail t = tail.getTail().get(i);
            if (t.getNode().getLayoutY() == imgY + addY
                    && t.getNode().getLayoutX() == imgX + addX) {
                return gameOver();
            }
        }
        if (OptionController.getWalltype().equals("No Walls")) {
            if (addY == 0) {
                return checkNoWallsX(addX);
            } else {
                return checkNoWallsY(addY);
            }
        } else {
            if (addY == 0) {
                return checkX(addX);
            } else {
                return checkY(addY);
            }
        }
    }

    /**
     * Stops the snake from moving when the game is over.
     */
    public boolean gameOver() throws Exception {
        update = false;
        fiveSecondsWonder.stop();
        openWindowSnakeDied();
        return false;
    }

    /**
     * Helper method for makeMove() to update wall coordinates.
     *
     * @param addY add value to y coordinate.
     * @return true if added.
     */
    public boolean checkNoWallsY(double addY) {
        if (node.getLayoutY() + 1 > Board.height - 20 && direction.equals(south)) {
            return updateYCoordinates(0);
        } else if (node.getLayoutY() - 1 < 0 && direction.equals(north)) {
            return updateYCoordinates(Board.height - 20);
        } else {
            return updateYCoordinates(node.getLayoutY() + addY);
        }
    }

    /**
     * Update the Y coordinate of the head of the snake.
     */
    public boolean updateYCoordinates(double value) {
        node.setLayoutY(value);
        Point2D p = new Point2D(node.getLayoutX(), node.getLayoutY());
        coordinates.add(0, p);
        return true;
    }

    /**
     * Update the X coordinate of the head of the snake.
     */
    public boolean updateXCoordinates(double value) {
        node.setLayoutX(value);
        Point2D p = new Point2D(node.getLayoutX(), node.getLayoutY());
        coordinates.add(0, p);
        return true;
    }

    /**
     * Helper method for makeMove() to update wall coordinates.
     *
     * @param addX add value to x coordinate.
     * @return true if added.
     */
    public boolean checkNoWallsX(double addX) {
        if ((node.getLayoutX() - 1 < 0 && direction.equals(west))) {
            return updateXCoordinates(Board.width - 20);
        } else if (node.getLayoutX() + 1 > Board.width - 20 && direction.equals(east)) {
            return updateXCoordinates(0);
        } else {
            return updateXCoordinates(node.getLayoutX() + addX);
        }
    }

    /**
     * Helper method for makeMove() to check for collision.
     *
     * @param addY add value to y coordinate.
     * @return true if added.
     * @throws Exception if picture not found.
     */
    public boolean checkY(double addY) throws Exception {
        if (node.getLayoutY() + 1 > Board.height - 20 && direction.equals(south)
                || node.getLayoutY() - 1 < 0 && direction.equals(north)) {
            return gameOver();
        } else {
            return updateYCoordinates(node.getLayoutY() + addY);
        }
    }

    /**
     * Helper method for makeMove() to check for collision.
     *
     * @param addX add value to x coordinate.
     * @return true if added.
     * @throws Exception if picture not found.
     */
    public boolean checkX(double addX) throws Exception {
        if ((node.getLayoutX() - 1 < 0 && direction.equals(west))
                || (node.getLayoutX() + 1 > Board.width - 20 && direction.equals(east))) {
            return gameOver();
        } else {
            return updateXCoordinates(node.getLayoutX() + addX);
        }
    }
}
