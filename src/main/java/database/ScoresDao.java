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


public class ScoresDao {
    private  DataSource dataSource;
    private DbConnect dbConnect;
    private  List<Scores> scores;
    private  List<Integer> scoresOfUser;
    private  List<Integer> scoresOfGameName;
    private  Integer highScore;
    private  Integer gameNameHighScore;
    private  HashMap<String, Integer> topGameNamesWithScores;

    /**
     * Constructor of ScoresDao.
     */
    public ScoresDao() {
        dbConnect = new DbConnect();
        this.dataSource = dbConnect.getDataSource();
        scores = new ArrayList<>();
        scoresOfUser = new ArrayList<>();
        highScore = 0;
        scoresOfGameName = new ArrayList<>();
        gameNameHighScore = 0;
        topGameNamesWithScores = new HashMap<>();

    }

    /**
     * Retrieve the scores from the database.
     *
     * @return List of scores
     */
    public List<Scores> retrieveScores() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT\n"
                    + " * FROM scores;");
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                scores.add(new Scores(resultSet.getInt(1), resultSet.getInt(2),
                         resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return scores;
    }

    /**
     * Retrieve all the scores the given user has achieved. A user can have multiple scores.
     *
     * @param username given username.
     * @return all the scores of the given user.
     */
    public List<Integer> getScoresOfUser(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT\n"
                             + " score\n"
                             + "FROM users, scores\n"
                             + "WHERE users.user_id = scores.user_id AND username = ?;")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    scoresOfUser.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return scoresOfUser;
    }


    /**
     * Retrieve highScore of user with given username.
     * One user can have multiple scores.
     *
     * @param username the given username.
     * @return the highScore of the given user.
     */
    public Integer highScoreOfUser(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT score\n"
                             + "FROM users,scores\n"
                             + "WHERE users.user_id = scores.user_id and username = ?\n"
                             + "ORDER BY score DESC LIMIT 1;")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    highScore = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return highScore;
    }

    /**
     * Retrieve all the scores from users with a gameName equal to the given one.
     * A gameName can have multiple scores.
     * @param gameName the given GameName.
     * @return all the scores achieved by the user with the given gameName.
     */
    public List<Integer> getScoresOfGameName(String gameName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT score\n"
                             + "FROM users, scores\n"
                             + "WHERE users.user_id = scores.user_id AND game_name = ?;")) {
            preparedStatement.setString(1, gameName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    scoresOfGameName.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return scoresOfGameName;
    }

    /**
     * Retrieve the highest score of the user with the given gameName.
     * A gameName can have multiple scores.
     * @param gameName the given GameName.
     * @return the highest score achieved by the user with the given gameName.
     */
    public Integer highScoreOfGameName(String gameName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT score\n"
                             + "FROM users,scores\n"
                             + "WHERE users.user_id = scores.user_id and game_name = ?\n"
                             + "ORDER BY score DESC LIMIT 1;")) {
            preparedStatement.setString(1, gameName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    gameNameHighScore = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return gameNameHighScore;
    }

    /**
     * Retrieve the gameNames of top 5 users with their highScores.
     *
     * @return a Map which contains the gameNames of the top 5 users.
     */
    public Map<String, Integer> retrieveGameNameHighScores() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT game_name, score \n"
                             + "FROM users,scores\n"
                             + "WHERE users.user_id = scores.user_id\n"
                             + "ORDER BY score DESC LIMIT 5;");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                topGameNamesWithScores.put(resultSet.getString(1),
                        resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Map.Entry<String, Integer>> listOfEntry = new
                LinkedList<>(topGameNamesWithScores.entrySet());
        Collections.sort(listOfEntry, (a, b) -> b.getValue().compareTo(a.getValue()));

        Map<String, Integer> sortedIdNameMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : listOfEntry) {
            sortedIdNameMap.put(entry.getKey(), entry.getValue());
        }
        return sortedIdNameMap;
    }

    /**
     * Insert given score with given UserId into database.
     *
     * @param userID the input UserId which needs to be inserted into the database.
     * @param score  the input score which needs to be inserted into the database.
     * @param gameName the input gameName which needs to be inserted into the database.
     * @return true if successful, false otherwise.
     */
    public boolean insertScore(int userID, int score, String gameName) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO scores "
                     + "VALUES (?, ?, ?);");) {
            ps.setInt(1, userID);
            ps.setInt(2, score);
            ps.setString(3, gameName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete given score with given UserId and gameName from database.
     *
     * @param userID the input UserID which needs to be deleted from the database.
     * @param score  the input score which needs to be deleted from the database.
     * @param gameName the input gameName which needs to be deleted from the database.
     * @return true if successful, false otherwise.
     */
    public boolean deleteScore(int userID, int score, String gameName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM scores "
                     + "WHERE user_id = ? AND score = ? AND game_name = ?;");) {
            ps.setInt(1, userID);
            ps.setInt(2, score);
            ps.setString(3, gameName);
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
     * @return true, if all scores were successfully removed, false otherwise.
     */
    public boolean clearScores() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM scores");) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
