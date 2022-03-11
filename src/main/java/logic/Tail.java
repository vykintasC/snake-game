package logic;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Tail {

    private Node node;

    /**
     * Create a new tail.
     *
     * @param x Set the image x position.
     * @param y Set the image y position.
     */
    public Tail(double x, double y) {
        if (UserPreferences.type.equals("Rectangle")) {
            node = new Rectangle(0, 0, 20, 20);
            ((Rectangle) node).setFill(UserPreferences.colortail);
        } else if (UserPreferences.type.equals("Default") || UserPreferences.type.equals("Image")) {
            node = new ImageView(UserPreferences.imagetail);
            ((ImageView) node).setFitHeight(20);
            ((ImageView) node).setFitWidth(20);
        }

        this.node.setLayoutX(x);
        this.node.setLayoutY(y);
    }

    /**
     * Resizes the tail picture according to the grid properties.
     *
     * @param ratio one square size.
     */
    public void showTail(int ratio) {
        node.prefWidth(ratio);
        node.prefHeight(ratio);
        node.autosize();
    }

    /**
     * Updates the coordinates of this to the predecessor tail.
     *
     * @param next predecessor tail.
     */
    public void updateCoordinates(Tail next) {
        this.node.setLayoutX(next.getNode().getLayoutX());
        this.node.setLayoutY(next.getNode().getLayoutY());
    }

    /**
     * Gets the image of the tail.
     *
     * @return image.
     */
    public Node getNode() {
        return this.node;
    }

    /**
     * Update first tail's coordinates to the snake's head's coordinates.
     *
     * @param head snake's head.
     */
    public void update(Snake head) {
        node.setLayoutX(head.getPreviousX());
        node.setLayoutY(head.getPreviousY());
    }
}
