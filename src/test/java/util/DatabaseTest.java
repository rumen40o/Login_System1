package util;


import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseTest {

    @Test
    void testGetConnection() {
        try {
            Connection connection = Database.getConnection();
            assertNotNull(connection, "Connection should not be null");
            connection.close(); // Always close the connection after use
        } catch (SQLException e) {
            e.printStackTrace();
            assert false : "Connection failed: " + e.getMessage();
        }
    }
}

