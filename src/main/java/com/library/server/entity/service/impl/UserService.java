package main.java.com.library.server.entity.service.impl;

import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.entity.impl.User;

public class UserService extends BaseService<User> {
    public UserService() {
        super(new BaseDao<>(User.class));
    }
}