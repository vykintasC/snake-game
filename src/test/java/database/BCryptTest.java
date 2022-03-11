package database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;


class BCryptTest {

    @Test
    void hashpw() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                BCrypt.hashpw("gaza", "bryan"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                BCrypt.hashpw("gaza", "$2sahk"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                BCrypt.hashpw("gaza", "$2a$&@@@@"));
        Assertions.assertEquals("$2a$12$4h23r9lkdndaiusfbbakfOsFy1/qBlHLDbJxqqQSVo.GAgW9HnCbu",
                BCrypt.hashpw("bran",
                        "$2a$12$4h23r9lkdndaiusfbbakfbinnn,kdsf,ba,fwfjkfbwlfwuhqih"));

    }

    @Test
    void gensalt() {
        Assertions.assertNotEquals(BCrypt.gensalt(), BCrypt.gensalt(10));
    }

    @Test
    void checkpw() {
        boolean hash = BCrypt.checkpw("bran",
                "$2a$12$4h23r9lkdndaiusfbbakfbinnn,kdsf,ba,fwfjkfbwlfwuhqih");
        Assertions.assertFalse(hash);
    }
}