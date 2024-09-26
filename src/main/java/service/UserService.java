package service;

import dao.UserDao;
import model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }


    public boolean registerUser(String firstName, String lastName, String email, String password) {

        if (firstName == null || lastName == null || email == null || password == null ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }


        if (userDao.getUserByEmail(email) != null) {
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

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
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
                user.setPassword(newPassword);
            }
            return userDao.updateUser(user);
        }
        return false;
    }

}
