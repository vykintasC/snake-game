package database;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsersTest {
    private  Users users;

    @BeforeEach
    void setUp() {
        users = new Users(12, "Gaza", "abc");
    }

    @Test
    void getUser_ID() {
        Assertions.assertEquals(12, users.getUser_ID());
    }

    @Test
    void setUser_ID() {
        users.setUser_ID(10);
        Assertions.assertEquals(10, users.getUser_ID());
    }

    @Test
    void getUsername() {
        Assertions.assertEquals("Gaza", users.getUsername());
    }

    @Test
    void setUsername() {
        users.setUsername("Bob");
        Assertions.assertEquals("Bob", users.getUsername());
    }

    @Test
    void getPassword() {
        Assertions.assertEquals("abc", users.getPassword());
    }

    @Test
    void setPassword() {
        users.setPassword("xyz");
        Assertions.assertEquals("xyz", users.getPassword());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("User_ID = " + users.getUser_ID() + ", Username = "
                + users.getUsername() + ", Password = " + users.getPassword(), users.toString());
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(Objects.hash(users.getUser_ID(), users.getUsername(),
                users.getPassword()), users.hashCode());
    }

    @Test
    void testEquals() {
        Users u1 = new Users(1, "John", "pass");
        Users u2 = new Users(1, "John", "pass");
        Assertions.assertTrue(u1.equals(u1));
        Users u = null;
        Scores s1 = new Scores(1, 20, "Snake");
        Assertions.assertFalse(u1.equals(s1));
        Assertions.assertFalse(u1.equals(u));
        Assertions.assertTrue(u1.equals(u2));
        Users u3 = new Users(4, "John", "pass");
        Assertions.assertFalse(u1.equals(u3));
        Assertions.assertTrue(u1.getUser_ID() == u2.getUser_ID()
                && u1.getUsername().equals(u2.getUsername())
                && u1.getPassword().equals(u2.getPassword()));
    }

    @Test
    void testEquals1() {
        Users u1 = new Users(4, "Abel", "klm");
        Users u2 = new Users(4, "Peter", "klm");
        Users u3 = new Users(4, "Abel", "xyz");
        Assertions.assertFalse(u1.equals(u2));
        Assertions.assertFalse(u1.equals(u3));
        Assertions.assertFalse(u2.equals(u3));
    }
}