import javafx.animation.Timeline;
import logic.Food;
import logic.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreTest {
    private static Score score;
    private  int points = 0;
    private static int cap1 = 5;
    private static int cap2 = 25;
    private Food food;

    @BeforeEach
    void setUp() {
        score = new Score();
        food = new Food();
    }

    @Test
    void getPoints() {
        Assertions.assertEquals(score.getPoints(), points);
    }

    @Test
    void increaseScore() {
        food.setPoints(5);
        score.increaseScore(food);
        Assertions.assertEquals(score.getPoints(), cap1);
        food.setPoints(20);
        score.increaseScore(food);
        Assertions.assertEquals(score.getPoints(), cap2);
    }

    @Test
    void increaseScore2() {
        food.setPoints(-1);
        score.increaseScore(food);
        Assertions.assertEquals(score.getPoints(), -1);
    }
}