import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

import logic.Tail;
import logic.UserPreferences;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TailTest {

    String img = "Image";
    String rect = "Rectangle";
    String def = "default";

    @Test
    void testConstructor() {
        UserPreferences.type = rect;
        Tail tail = new Tail(0, 0);
        Tail tail1 = new Tail(0, 0);
        Assertions.assertEquals(tail.getNode().getLayoutX(),
                tail1.getNode().getLayoutX());
        Assertions.assertEquals(tail.getNode().getLayoutY(),
                tail1.getNode().getLayoutY());
    }

    @Test
    void testConstructor2() {
        UserPreferences.type = img;
        Tail tail = new Tail(0, 0);
        Tail tail1 = new Tail(0, 0);
        Assertions.assertEquals(tail.getNode().getLayoutX(),
                tail1.getNode().getLayoutX());
        Assertions.assertEquals(tail.getNode().getLayoutY(),
                tail1.getNode().getLayoutY());
    }

    @Test
    void testConstructor3() throws FileNotFoundException {
        UserPreferences.type = img;
        UserPreferences.imagehead = new Image(new
                FileInputStream("default_head.png"));
        Tail tail = new Tail(0, 0);
        Tail tail1 = new Tail(0, 0);
        Assertions.assertEquals(tail.getNode().getLayoutX(),
                tail1.getNode().getLayoutX());
        Assertions.assertEquals(tail.getNode().getLayoutY(),
                tail1.getNode().getLayoutY());
    }

    //    @Test
    //    void testConstructor4() throws FileNotFoundException {
    //        UserPreferences.type = rect;
    //        UserPreferences.imagetail = new Image(new
    //                FileInputStream("default_tail.jpg"));
    //        Assertions.assertThrows(NullPointerException.class, () ->
    //                new Tail(0, 0));
    //    }
    //
    //    @Test
    //    void testConstructor5() throws FileNotFoundException {
    //        UserPreferences.type = img;
    //        UserPreferences.imagefood = new Image(new
    //                FileInputStream("default_food.png"));
    //        Assertions.assertThrows(NullPointerException.class, () ->
    //                new Tail(0, 0));
    //    }
}