package dao;

import model.User;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    // Save a new user to the database
    public boolean saveUser(User user) {
        String query = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user by email for login
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // No user found with the given email
    }

    // Get user by ID for updating user profile
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // No user found with the given ID
    }

    // Update the user's details in the database
    public boolean updateUser(User user) {
        String query = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
