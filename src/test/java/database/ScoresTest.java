package database;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ScoresTest {
    private  Scores scores;

    @BeforeEach
    void setUp() {
        scores = new Scores(123, 50, "Snake");
    }

    @Test
    void getUser_ID() {
        Assertions.assertEquals(123, scores.getUser_ID());
    }

    @Test
    void setUser_ID() {
        scores.setUser_ID(120);
        Assertions.assertEquals(120, scores.getUser_ID());
    }

    @Test
    void getScore() {
        Assertions.assertEquals(50, scores.getScore());
    }

    @Test
    void setScore() {
        scores.setScore(100);
        Assertions.assertEquals(100, scores.getScore());
    }

    @Test
    void getGameName() {
        Assertions.assertEquals("Snake", scores.getGameName());
    }

    @Test
    void setGameName() {
        scores.setGameName("BattleShip");
        Assertions.assertEquals("BattleShip", scores.getGameName());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("User_ID = " + scores.getUser_ID()
                        + ", Score = " + scores.getScore() + ", GameName = " + scores.getGameName(),
                scores.toString());
    }

    @Test
    void testEquals() {
        Scores u1 = new Scores(1, 50, "Bubble");
        Scores u2 = new Scores(1, 50, "Bubble");
        Users a = new Users(1, "Pablo", "password");
        Assertions.assertFalse(u1.equals(a));
        Assertions.assertTrue(u1.equals(u1));
        Scores u = null;
        Assertions.assertFalse(u1.equals(u));
        Assertions.assertTrue(u1.equals(u2));
        Scores u3 = new Scores(4, 40, "Asteroids");
        Assertions.assertFalse(u1.equals(u3));
        Assertions.assertTrue(u1.getUser_ID() == u2.getUser_ID()
                && u1.getScore() == u2.getScore() && u1.getGameName()
                .equals(u2.getGameName()));
    }

    @Test
    void testEquals1() {
        Scores u1 = new Scores(1, 50, "Bricks");
        Scores u2 = new Scores(2, 50, "Pool");
        Scores u3 = new Scores(1, 60, "MarioKart");
        Scores u4 = new Scores(2,50,"MarioKart");
        Assertions.assertFalse(u1.equals(u2));
        Assertions.assertFalse(u1.equals(u3));
        Assertions.assertFalse(u1.equals(u4));
        Assertions.assertFalse(u2.equals(u4));
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(Objects.hash(scores.getUser_ID(),
                scores.getScore(), scores.getGameName()), scores.hashCode());
    }
}