package main.java.com.library.server.service.impl;

import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.entity.impl.User;

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

    public User getUserByEmailOrUsername(String identifier) {
        User user = getUserByUsername(identifier);
        if (user == null) {
            user = getUserByEmail(identifier);
        }
        return user;
    }

    public boolean validateUser(String identifier, String password) {
        User user = getUserByEmailOrUsername(identifier);
        return user != null && user.getPassword().equals(password);
    }


    public boolean isAdmin(String identifier) {
        User user = getUserByEmailOrUsername(identifier);
        return user != null && "admin".equals(user.getRole());
    }


}
