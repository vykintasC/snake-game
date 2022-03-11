package database;

import java.util.Objects;

public class Users {

    private int userID;
    private String username;
    private String password;

    /**
     * Constructor of users object.
     *
     * @param userID  user id.
     * @param userName username.
     * @param password user password.
     */
    public Users(int userID, String userName, String password) {
        this.userID = userID;
        this.username = userName;
        this.password = password;
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
     * getter for username.
     *
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username.
     *
     * @param username given username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for password.
     *
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        return userID == users.userID
                && Objects.equals(getUsername(), users.getUsername())
                && Objects.equals(getPassword(), users.getPassword());
    }

    /**
     * setter for password.
     *
     * @param password given password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Users object as string format.
     *
     * @return human-friendly string representation for Users object.
     */
    public String toString() {
        return "User_ID = " + userID + ", Username = " + username + ", Password = " + password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, getUsername(), getPassword());
    }
}