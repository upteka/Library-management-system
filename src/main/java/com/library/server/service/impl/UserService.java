package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.User;
import main.java.com.library.server.database.impl.BaseDao;

import java.sql.SQLException;

/**
 * @author PC
 */
public class UserService extends BaseService<User> {
    public UserService() {
        super(new BaseDao<>(User.class));
    }

    public User getUserByUsername(String username) {
        return super.getByField("username", username);
    }

    public User getUserByEmail(String email) {
        return super.getByField("email", email);
    }

    public User getUserByPhone(String phone) {
        return super.getByField("phone", phone);
    }


    public User getUser(String identifier) {
        User user = getUserByUsername(identifier);
        if (user == null) {
            user = getUserByEmail(identifier);
            if (user == null) {
                user = getUserByPhone(identifier);
            }
        }

        return user;
    }


    public boolean validateUser(String identifier, String password) {
        User user = getUser(identifier);
        return user != null && user.getPassword().equals(password);
    }

    public boolean isUserExists(String identifier) {
        return getUser(identifier) != null;
    }


    public boolean isAdmin(String identifier) {
        User user = getUser(identifier);
        return user != null && "admin".equals(user.getRole());
    }

    public String registerUser(User user) throws SQLException {
        if (isUserExists(user.getUsername())) {
            return "Failed to register user: Username already exists";
        } else if (isUserExists(user.getEmail())) {
            return "Failed to register user: Email already exists";
        } else if (isUserExists(user.getPhone())) {
            return "Failed to register user: Phone number already exists";
        } else if (user.getPassword().length() < 8) {
            return "Failed to register user: Password must be at least 8 characters";
        } else {
            user.setRole("admin");
            if (super.add(user).equals("Success")) {
                return "Success: User registered successfully";
            } else {
                return "Failed to register user: Internal server error";
            }
        }
    }


}
