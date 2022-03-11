package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize, PMD.CloseResource"})
public class UsersDao {
    private  DataSource dataSource;
    private  DbConnect dbConnect;
    private  List<Users> users;
    private  Map<String, Integer> topUsersWithScores;
    private  List<String> usersWithScore;


    /**
     * Constructor of UsersDao.
     */
    public UsersDao() {
        dbConnect = new DbConnect();
        this.dataSource = dbConnect.getDataSource();
        users = new ArrayList<>();
        topUsersWithScores = new HashMap<>();
        usersWithScore = new ArrayList<>();
    }


    /**
     * Retrieve the users from the database.
     *
     * @return List of users
     */
    public List<Users> retrieveUsers() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users;");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(new Users(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    /**
     * Retrieve the user names of users who have scored the given amount of points.
     * One score can be achieved by multiple users.
     *
     * @param score the given score.
     * @return the list of user names from users who have achieved the given score.
     */
    public List<String> getUsersWithScore(int score) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT username\n"
                             + "FROM users, scores\n"
                             + "WHERE users.user_id = scores.user_id AND score = ?;")) {
            preparedStatement.setInt(1, score);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    usersWithScore.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return usersWithScore;
    }

    /**
     * Retrieve the top 5 users with their highScores.
     *
     * @return a Map which contains the top 5 users.
     */
    public Map<String, Integer> retrieveHighScores() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT username, score \n"
                             + "FROM users,scores\n"
                             + "WHERE users.user_id = scores.user_id\n"
                             + "ORDER BY score DESC LIMIT 5;");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                topUsersWithScores.put(resultSet.getString(1),
                        resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        List<Map.Entry<String, Integer>> listOfEntry = new
                LinkedList<>(topUsersWithScores.entrySet());
        Collections.sort(listOfEntry, (a, b) -> a.getValue().compareTo(b.getValue()));

        Map<String, Integer> sortedIdNameMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : listOfEntry) {
            sortedIdNameMap.put(entry.getKey(), entry.getValue());
        }
        return sortedIdNameMap;
    }

    /**
     * Insert the given user credentials into users table.
     *
     * @param username username.
     * @param password user password.
     * @return true if user is successfully inserted, false otherwise.
     */
    public boolean insertUser(String username, String password) {
        if (isExisting(username)) {
            return false;
        } else {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement ps = connection.prepareStatement("INSERT INTO users "
                         + "VALUES (default, ?, ?);")) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    /**
     * Delete given user credentials from users table.
     *
     * @param username username.
     * @param password password.
     * @return true if user is successfully deleted, false otherwise.
     */
    public boolean deleteUser(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM users "
                     + "WHERE username = ? AND password = ?;")) {
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Change the password to the new given one of the given username.
     *
     * @param username User whose password needs to be changed.
     * @param password new Password.
     * @return true if successful, false otherwise.
     */
    public boolean changePassword(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE users "
                     + "SET password = ? WHERE username = ?")) {
            ps.setString(1, password);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * clear whole table by removing all tuples.
     *
     * @return true, if all users were successfully removed, false otherwise.
     */
    public boolean clearUsers() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM users")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Login user.
     *
     * @param username of the player.
     * @param password of the player.
     * @return true if login was successful, otherwise false.
     */
    public Users login(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT username, password, user_id"
                             + " FROM users WHERE username like ?")) {
            ps.setString(1, username);
            ResultSet result = ps.executeQuery();
            result.next();
            String pass = result.getString("password");
            if (BCrypt.checkpw(password, pass)) {
                return new Users(result.getInt("user_id"), username, "won't hack it :)");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a user is registering an unused username.
     *
     * @param username to be checked.
     * @return false if this username doesn't exist in database.
     */
    public boolean isExisting(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT username FROM users")) {
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String user = result.getString("username");
                if (user.equals(username)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}
