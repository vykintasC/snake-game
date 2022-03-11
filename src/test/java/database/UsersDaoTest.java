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


class UsersDaoTest {
    @InjectMocks
    private UsersDao usersDao;

    @Mock
    private  Connection connection;

    @Mock
    private DbConnect mockConnection;

    @Mock
    private  DataSource dataSource;

    @Mock
    private  PreparedStatement mockStatement;

    @Mock
    private  ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveUsers() throws SQLException {
        //check for SQLException when no users are retrieved.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        List<Users> l = usersDao.retrieveUsers();
        Assertions.assertNull(l);
    }

    @Test
    void retrieveUsers2() throws SQLException {
        //check for case where users are retrieved from database by mocking its state.
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        List<Users> a = new ArrayList<>();
        a.add(new Users(1,"test", "password"));
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1);
        Mockito.when(resultSet.getString(2)).thenReturn("test");
        Mockito.when(resultSet.getString(3)).thenReturn("password");
        Assertions.assertEquals(a, usersDao.retrieveUsers());
    }

    @Test
    void getUsersWithGivenScore() throws SQLException {
        //retrieve users who have achieved a given score from database by mocking its state.
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        List<String> l = new ArrayList<>();
        l.add("Jazz");
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getString(1)).thenReturn("Jazz");
        Assertions.assertEquals(l, usersDao.getUsersWithScore(10));
    }

    @Test
    void getUsersWithGivenScore2() throws SQLException {
        //check for SQLException when there are no users with the given score.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        List<String> list = usersDao.getUsersWithScore(10);
        Assertions.assertNull(list);
    }

    @Test
    void retrieveHighScores() throws SQLException {
        //retrieve the high scores of the database by mocking its state.
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(resultSet);
        LinkedHashMap<String, Integer> h = new LinkedHashMap<>();
        h.put("John", 100);
        h.put("Bryan", 50);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getString(1)).thenReturn("John").thenReturn("Bryan");
        Mockito.when(resultSet.getInt(2)).thenReturn(100).thenReturn(50);
        Assertions.assertEquals(h, usersDao.retrieveHighScores());
    }

    @Test
    void retrieveHighScores2() throws SQLException {
        //check for case where there are no high scores.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Map<String, Integer> linkedHashMap = usersDao.retrieveHighScores();
        Assertions.assertNull(linkedHashMap);
    }



    @Test
    void insertUser() throws SQLException {
        //insert user into database by mocking its state.
        ResultSet set = Mockito.mock(ResultSet.class);
        Mockito.when(set.next()).thenReturn(true).thenReturn(false);
        Mockito.when(set.getString("username")).thenReturn("Someone");
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(set);
        Assertions.assertEquals(usersDao.insertUser("Walt", "xyz"), true);
    }

    @Test
    void insertUser2() throws SQLException {
        //check for case where user cannot be inserted.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(usersDao.insertUser("Walt", "abc"), false);
    }

    @Test
    void deleteUser() throws SQLException {
        //delete user from database by mocking its state.
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Assertions.assertEquals(usersDao.deleteUser("Bar", "abx"), true);
    }

    @Test
    void deleteUser2() throws SQLException {
        //check for case where user is not found in database by mocking its state.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(usersDao.deleteUser("Bar", "abx"), false);
    }

    @Test
    void changePassword() throws SQLException {
        //change password of given user by mocking the state if the database.
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Assertions.assertEquals(usersDao.changePassword("Ben", "xyz"), true);
    }

    @Test
    void changePassword2() throws SQLException {
        //check for case where password cannot be changed.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(usersDao.changePassword("Ben", "password"), false);
    }

    @Test
    void clearUsers() throws SQLException {
        //delete all users from database by mocking its state.
        Mockito.when(mockConnection.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Assertions.assertEquals(usersDao.clearUsers(), true);
    }

    @Test
    void clearUsers2() throws SQLException {
        //check for case where database user table cannot be cleared.
        Mockito.doReturn(dataSource).when(mockConnection).getDataSource();
        Mockito.doThrow(new SQLException()).when(dataSource).getConnection();
        Assertions.assertEquals(usersDao.clearUsers(), false);
    }
}