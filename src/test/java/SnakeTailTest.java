import java.util.ArrayList;

import logic.Food;
import logic.Snake;
import logic.SnakeTail;
import logic.Tail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeTailTest {

    private SnakeTail tails;
    private Tail tail;
    private Snake head;

    @BeforeEach
    void setUp() {
        tails = new SnakeTail();
        head = new Snake();
        head.createSnake(400, 400, 20);
        tail = new Tail(head.getNode().getLayoutX() + 20,
                head.getNode().getLayoutY());
        tail.showTail(20);
    }

    @Test
    void testConstructor() {
        SnakeTail t = new SnakeTail();

        Assertions.assertEquals(0, t.getSize());
    }

    @Test
    void add() {
        tails.add(tail);

        Assertions.assertTrue(tails.getSize() > 0);
    }

    @Test
    void update() throws Exception {
        tails.add(tail);
        tails.add(new Tail(head.getNode().getLayoutX() + 40,
                head.getNode().getLayoutY()));

        Food food = new Food();
        food.createFood(400, 400, 20);

        head.setSnakeTail(tails);

        head.move("West", food);

        double tailx = tail.getNode().getLayoutX();
        double taily = tail.getNode().getLayoutY();

        tails.update(head, false, null);

        Assertions.assertEquals(tails.getTail(1).getNode().getLayoutX(), tailx);
        Assertions.assertEquals(tails.getTail(1).getNode().getLayoutY(), taily);
        Assertions.assertEquals(tails.getTail(0).getNode()
                .getLayoutX(), head.getPreviousX());
        Assertions.assertEquals(tails.getTail(0).getNode()
                .getLayoutY(), head.getPreviousY());
    }

    @Test
    void update2() throws Exception {
        Assertions.assertEquals(new ArrayList<>(), tails.getTail());
        tails.add(new Tail(head.getNode().getLayoutX() + 40,
                head.getNode().getLayoutY()));
        tails.add(tail);
        Food food = new Food();
        food.createFood(400, 400, 20);
        head.setSnakeTail(tails);
        head.move("West", food);
        Assertions.assertThrows(NullPointerException.class, () ->
                tails.update(head, true, null));
    }

}