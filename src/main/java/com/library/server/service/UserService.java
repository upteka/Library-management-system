package main.java.com.library.server.service;

import main.java.com.library.server.database.impl.BaseDAO;
import main.java.com.library.server.model.User;

import java.util.List;

public class UserService {
    private final BaseDAO<User> UserDAO = new BaseDAO<>(User.class);

    public boolean addUser(User User) {
        return UserDAO.add(User);
    }

    public boolean deleteUser(String UserID) {
        return UserDAO.delete(UserID);
    }

    public User get(String UserID) {
        return UserDAO.get(UserID);
    }


    public List<User> getAll() {
        return UserDAO.getAll();
    }

    public boolean update(User User) {
        return UserDAO.update(User);
    }

    public User getBy(String field, String username) {
        return UserDAO.getByField(field, username);
    }
}