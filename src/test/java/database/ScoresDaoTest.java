package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class ScoresDaoTest {

    @InjectMocks
    private ScoresDao scoresDao;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private DbConnect dbConnect;

    @Mock
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveScores() throws SQLException {
        //check for SQLException if no score is found.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertNull(scoresDao.retrieveScores());
    }

    @Test
    void retrieveScores2() throws SQLException {
        //check for correct score retrievals from the database by mocking the state of the database.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        List<Scores> a = new ArrayList<>();
        a.add(new Scores(1, 50, "logic.Snake"));
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1);
        Mockito.when(resultSet.getInt(2)).thenReturn(50);
        Mockito.when(resultSet.getString(3)).thenReturn("logic.Snake");
        Assertions.assertEquals(a, scoresDao.retrieveScores());
    }

    @Test
    void getScoresOfUser() throws SQLException {
        //check for correct score retrieval of a given user
        //from the database by mocking the state of the database.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        List<Integer> res = new ArrayList<>();
        res.add(150);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(150);
        Assertions.assertEquals(res, scoresDao.getScoresOfUser("Bryan"));
    }

    @Test
    void getScoresOfUser2() throws SQLException {
        //check for SQLException if no score is found.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertNull(scoresDao.getScoresOfUser("Bryan"));
    }

    @Test
    void highScoreOfUser() throws SQLException {
        //check for high score of a given user.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        Integer integer = 300;
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(300);
        Assertions.assertEquals(integer, scoresDao.highScoreOfUser("Jack"));
    }

    @Test
    void highScoreOfUser2() throws SQLException {
        //check for SQLException if no score is found.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertNull(scoresDao.highScoreOfUser("Jax"));
    }

    @Test
    void getScoresOfGameName() throws SQLException {
        //check for score retrievals for a given game name.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        List<Integer> res = new ArrayList<>();
        res.add(400);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(400);
        Assertions.assertEquals(res, scoresDao.getScoresOfGameName("Pac-man"));
    }

    @Test
    void getScoresOfGameName2() throws SQLException {
        //check for SQLException if no score is found.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertNull(scoresDao.getScoresOfGameName("Pac-man"));
    }

    @Test
    void highScoreOfGameName() throws SQLException {
        //check for high score retrieval of the given game name.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        Integer integer = 460;
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(460);
        Assertions.assertEquals(integer, scoresDao.highScoreOfGameName("Mario-Party"));
    }

    @Test
    void highScoreOfGameName2() throws SQLException {
        //check for SQLException if no score is found.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertNull(scoresDao.highScoreOfGameName("Mario-Party"));
    }

    @Test
    void retrieveGameNameHighScores() throws SQLException {
        //check for high score retrievals of given game name.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        LinkedHashMap<String, Integer> h = new LinkedHashMap<>();
        h.put("PacMan", 700);
        h.put("BattleShip", 650);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getString(1)).thenReturn("PacMan").thenReturn("BattleShip");
        Mockito.when(resultSet.getInt(2)).thenReturn(700).thenReturn(650);
        Assertions.assertEquals(h, scoresDao.retrieveGameNameHighScores());
    }

    @Test
    void retrieveGameNameHighScores2() throws SQLException {
        //check for SQLException if no score is found.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Map<String, Integer> linkedHashMap = scoresDao.retrieveGameNameHighScores();
        Assertions.assertNull(linkedHashMap);
    }

    @Test
    void insertScore() throws SQLException {
        //insert score into database.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Assertions.assertEquals(scoresDao.insertScore(1, 40,
                "BattleShip"), true);
    }

    @Test
    void insertScore2() throws SQLException {
        //check for case where score cannot be inserted.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(scoresDao.insertScore(1, 40, "BubbleShooter"),
                false);
    }

    @Test
    void deleteScore() throws SQLException {
        //delete score from database.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Assertions.assertEquals(scoresDao.deleteScore(1, 40, "Asteroids"),
                true);
    }

    @Test
    void deleteScore2() throws SQLException {
        //check for case where score cannot be deleted.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(scoresDao.deleteScore(1, 40, "Bricks"),
                false);
    }

    @Test
    void clearScores() throws SQLException {
        //delete all scores from the database.
        Mockito.when(dbConnect.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockStatement);
        Assertions.assertEquals(scoresDao.clearScores(), true);
    }

    @Test
    void clearScores2() throws SQLException {
        //check for case where scores of database cannot be cleared.
        Mockito.doReturn(dataSource).when(dbConnect).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(scoresDao.clearScores(), false);
    }
}