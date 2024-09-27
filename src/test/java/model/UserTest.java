package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User("John", "Doe", "john.doe@example.com", "password123");
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password456");

        assertEquals(1, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("password456", user.getPassword());
    }

    @Test
    void testSetFirstNameWithNull() {
        User user = new User();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setFirstName(null);
        });
        assertEquals("First name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testSetLastNameWithNull() {
        User user = new User();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setLastName(null);
        });
        assertEquals("Last name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testSetEmailWithNull() {
        User user = new User();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail(null);
        });
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void testSetPasswordWithNull() {
        User user = new User();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword(null);
        });
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

}
