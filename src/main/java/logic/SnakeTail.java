package logic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class SnakeTail {

    private  List<Tail> tail;

    /**
     * Constructor.
     */
    public SnakeTail() {
        tail = new ArrayList<>();
    }

    /**
     * Return the size of the list.
     * @return size.
     */
    public int getSize() {
        return tail.size();
    }

    /**
     * Get the specific tail from the list.
     * @param i index of the tail.
     * @return the tail at that index.
     */
    public Tail getTail(int i) {
        return tail.get(i);
    }

    /**
     * Get the tail list.
     * @return the entire tail of the snake
     */
    public List<Tail> getTail() {
        return tail;
    }

    /**
     * Adds tail to the end of the list.
     * @param next tail to add.
     */
    public void add(Tail next) {
        tail.add(next);
    }

    /**
     * updates the position of the tail of the snake.
     *
     * @param head the head of the snake the user is controlling.
     * @param root the pane we draw the snake on.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void update(Snake head, boolean grow, Pane root) {
        double newX = getTail(getSize() - 1).getNode().getLayoutX();
        double newY = getTail(getSize() - 1).getNode().getLayoutY();

        for (int i = getSize() - 1; i >= 1; i--) {
            getTail(i).updateCoordinates(getTail(i - 1));
        }

        getTail(0).update(head);

        if (grow) {
            grow(newX, newY, root);
        }
    }

    /**
     * Grows the size of the snake by adding a new tail element at the end.
     *
     * @param x    the x coordinate where the new tail end will spawn.
     * @param y    the y coordinate where the new tail end will spawn.
     * @param root the root tile we need to add the image of the tail to.
     */
    public void grow(double x, double y, Pane root) {
        Tail newTail = new Tail(x, y);
        newTail.showTail(20);
        add(newTail);
        root.getChildren().add(newTail.getNode());
    }

}
