package service;


import dao.UserDao;
import model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();  // Initializing the DAO
    }


    public boolean registerUser(String firstName, String lastName, String email, String password) {
        // Validate the inputs (basic validation, can be extended)
        if (firstName == null || lastName == null || email == null || password == null ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        User user = new User(firstName, lastName, email, password);
        return userDao.saveUser(user);
    }




    public User loginUser(String email, String password) {

        User user = userDao.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public boolean updateUser(int userId, String newFirstName, String newLastName, String newPassword) {
        // Get the user by ID
        User user = userDao.getUserById(userId);

        if (user != null) {
            // Update the user's details
            if (newFirstName != null && !newFirstName.isEmpty()) {
                user.setFirstName(newFirstName);
            }
            if (newLastName != null && !newLastName.isEmpty()) {
                user.setLastName(newLastName);
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }
            return userDao.updateUser(user);  // Update in the database
        }

        return false;
    }
}
