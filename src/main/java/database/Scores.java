package database;

import java.util.Objects;

public class Scores {
    private  int userID;
    private int score;
    private String gameName;


    /**
     * constructor of scores object.
     *
     * @param userID user id.
     * @param score  score.
     */
    public Scores(int userID, int score, String gameName) {
        this.userID = userID;
        this.score = score;
        this.gameName = gameName;
    }

    /**
     * getter for user id.
     *
     * @return user id.
     */
    public int getUser_ID() {
        return userID;
    }

    /**
     * setter for user id.
     *
     * @param userID given user id.
     */
    public void setUser_ID(int userID) {
        this.userID = userID;
    }

    /**
     * getter for score.
     *
     * @return score.
     */
    public int getScore() {
        return score;
    }

    /**
     * setter for score.
     *
     * @param score given score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * getter for gameName.
     *
     * @return gameName.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * setter for gameName.
     *
     * @param gameName the given gameName.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Scores object as string format.
     *
     * @return human-friendly string representation for Scores object.
     */
    public String toString() {
        return "User_ID = " + userID + ", Score = " + score
                + ", GameName = " + gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scores scores = (Scores) o;
        return userID == scores.userID
                && getScore() == scores.getScore()
                && Objects.equals(getGameName(), scores.getGameName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, getScore(), getGameName());
    }

}