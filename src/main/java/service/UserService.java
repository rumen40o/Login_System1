package service;

import dao.UserDao;
import model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();  // Initializing the DAO
    }

    // Register a new user
    public boolean registerUser(String firstName, String lastName, String email, String password) {
        // Validate the inputs (basic validation, can be extended)
        if (firstName == null || lastName == null || email == null || password == null ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        // Check if the user already exists
        if (userDao.getUserByEmail(email) != null) {
            return false; // Email already exists
        }

        // Create a new user and save it in the database
        User user = new User(firstName, lastName, email, password);
        return userDao.saveUser(user);
    }

    // Authenticate a user by their email and password
    public User loginUser(String email, String password) {
        User user = userDao.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;  // Invalid credentials
    }


    public User getUserById(int userId) {
        return userDao.getUserById(userId); // Implement this method in UserDao to fetch user by ID
    }

    public boolean updateUser(int userId, String newFirstName, String newLastName, String newPassword) {
        User user = userDao.getUserById(userId);

        if (user != null) {
            if (newFirstName != null && !newFirstName.isEmpty()) {
                user.setFirstName(newFirstName);
            }
            if (newLastName != null && !newLastName.isEmpty()) {
                user.setLastName(newLastName);
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword); // Password change
            }
            return userDao.updateUser(user); // Update in the database
        }
        return false;
    }

}
