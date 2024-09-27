package service;

import dao.UserDao;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao); // Assuming constructor injection
    }

    @Test
    void testRegisterUser_Success() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";

        when(userDao.getUserByEmail(email)).thenReturn(null);
        when(userDao.saveUser(any(User.class))).thenReturn(true);

        boolean result = userService.registerUser(firstName, lastName, email, password);
        assertTrue(result);

        // Capture the User object passed to saveUser
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).saveUser(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(firstName, savedUser.getFirstName());
        assertEquals(lastName, savedUser.getLastName());
        assertEquals(email, savedUser.getEmail());
        assertEquals(password, savedUser.getPassword());
    }

    @Test
    void testRegisterUser_UserExists() {
        String email = "existing.user@example.com";

        when(userDao.getUserByEmail(email)).thenReturn(new User("Jane", "Doe", email, "existing.password"));

        boolean result = userService.registerUser("Jane", "Doe", email, "password123");
        assertFalse(result);
    }

    @Test
    void testLoginUser_Success() {
        String email = "john.doe@example.com";
        String password = "password123";
        User user = new User("John", "Doe", email, password);

        when(userDao.getUserByEmail(email)).thenReturn(user);

        User result = userService.loginUser(email, password);
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testLoginUser_InvalidPassword() {
        String email = "john.doe@example.com";
        User user = new User("John", "Doe", email, "wrongpassword");

        when(userDao.getUserByEmail(email)).thenReturn(user);

        User result = userService.loginUser(email, "password123");
        assertNull(result);
    }

    @Test
    void testLoginUser_UserNotFound() {
        String email = "unknown.user@example.com";

        when(userDao.getUserByEmail(email)).thenReturn(null);

        User result = userService.loginUser(email, "password123");
        assertNull(result);
    }

    @Test
    void testGetUserById_Success() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", "password123");

        when(userDao.getUserById(1)).thenReturn(user);

        User result = userService.getUserById(1);
        assertEquals(user, result);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userDao.getUserById(2)).thenReturn(null);

        User result = userService.getUserById(2);
        assertNull(result);
    }

    @Test
    void testUpdateUser_Success() {
        User existingUser = new User(1, "John", "Doe", "john.doe@example.com", "password123");

        when(userDao.getUserById(1)).thenReturn(existingUser);
        when(userDao.updateUser(existingUser)).thenReturn(true);

        boolean result = userService.updateUser(1, "Johnny", "Doe", "newpassword123");
        assertTrue(result);
        assertEquals("Johnny", existingUser.getFirstName());
        assertEquals("Doe", existingUser.getLastName());
        assertEquals("newpassword123", existingUser.getPassword());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userDao.getUserById(1)).thenReturn(null);

        boolean result = userService.updateUser(1, "Johnny", "Doe", "newpassword123");
        assertFalse(result);
    }

    @Test
    void testUpdateUser_NullValues() {
        User existingUser = new User(1, "John", "Doe", "john.doe@example.com", "password123");

        when(userDao.getUserById(1)).thenReturn(existingUser);
        when(userDao.updateUser(existingUser)).thenReturn(true);

        boolean result = userService.updateUser(1, null, null, null);
        assertTrue(result); // Should return true since we are not changing anything
        assertEquals("John", existingUser.getFirstName()); // No change
        assertEquals("Doe", existingUser.getLastName()); // No change
        assertEquals("password123", existingUser.getPassword()); // No change
    }
}
