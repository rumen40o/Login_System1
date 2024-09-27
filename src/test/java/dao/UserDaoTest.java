package dao;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.mockito.MockedStatic;
import util.Database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDaoTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private UserDao userDao;

    private MockedStatic<Database> mockedDatabase; // Declare the static mock

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        userDao = new UserDao();

        // Mock Database class to return mock connection
        mockedDatabase = mockStatic(Database.class); // Create static mock here
        mockedDatabase.when(Database::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    public void tearDown() {
        if (mockedDatabase != null) {
            mockedDatabase.close(); // Close the static mock to deregister it
        }
    }

    @Test
    public void testSaveUser() throws SQLException {
        // Arrange
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = userDao.saveUser(user);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setString(1, user.getFirstName());
        verify(mockPreparedStatement).setString(2, user.getLastName());
        verify(mockPreparedStatement).setString(3, user.getEmail());
        verify(mockPreparedStatement).setString(4, user.getPassword());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetUserByEmail() throws SQLException {
        String email = "john.doe@example.com";
        String query = "SELECT * FROM users WHERE email = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("first_name")).thenReturn("John");
        when(mockResultSet.getString("last_name")).thenReturn("Doe");
        when(mockResultSet.getString("email")).thenReturn(email);
        when(mockResultSet.getString("password")).thenReturn("password123");

        User user = userDao.getUserByEmail(email);

        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testGetUserById() throws SQLException {
        int userId = 1;
        String query = "SELECT id, first_name, last_name, password FROM users WHERE id = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("first_name")).thenReturn("John");
        when(mockResultSet.getString("last_name")).thenReturn("Doe");
        when(mockResultSet.getString("password")).thenReturn("password123");

        User user = userDao.getUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("newpassword123");

        String query = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE id = ?";
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // simulate one row updated

        boolean result = userDao.updateUser(user);

        assertTrue(result);
        verify(mockPreparedStatement, times(1)).setString(1, "Jane");
        verify(mockPreparedStatement, times(1)).setString(2, "Doe");
        verify(mockPreparedStatement, times(1)).setString(3, "newpassword123");
        verify(mockPreparedStatement, times(1)).setInt(4, 1);
    }
}
