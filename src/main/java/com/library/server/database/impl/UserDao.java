package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.impl.User;

public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }
}

