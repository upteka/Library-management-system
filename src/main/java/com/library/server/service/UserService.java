package main.java.com.library.server.service;

import main.java.com.library.server.database.UserDAO;
import main.java.com.library.server.model.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        // 检查用户名是否已存在
        if (userDAO.getUser(user.getUsername()) != null) {
            return false; // 用户名已存在，注册失败
        }
        return userDAO.addUser(user);
    }

    public boolean login(String username, String password) {
        User user = userDAO.getUser(username);
        return user != null && user.getPassword().equals(password);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUser(String userID) {
        return userDAO.deleteUser(userID);
    }

    public User getUser(String identifier) {
        return userDAO.getUser(identifier);
    }

    public boolean updateUser(User user) {
        User existingUser = userDAO.getUser(user.getUserID());
        if (existingUser != null) {
            // 仅当提供新值时更新字段
            if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
            if (user.getRole() != null) existingUser.setRole(user.getRole());

            return userDAO.updateUser(existingUser);
        }
        return false;
    }

    public boolean updatePassword(String userID, String newPassword) {
        return userDAO.updatePassword(userID, newPassword);
    }

    public boolean updateRole(String userID, String newRole) {
        return userDAO.updateRole(userID, newRole);
    }
}