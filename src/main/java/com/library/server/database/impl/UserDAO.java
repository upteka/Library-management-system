package main.java.com.library.server.database.impl;

import main.java.com.library.server.model.User;

public class serDAO extends BaseDAO<User> {
    public UserDAO() {
        super(User.class);
    }
}

