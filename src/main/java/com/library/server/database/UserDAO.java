package main.java.com.library.server.database;

import main.java.com.library.server.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends BaseDAO<User> {

    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("userID"),
                rs.getString("username"),
                rs.getString("password")
        );
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO users (userID, username, password, role) VALUES (?, ?, ?, ?)";
        return executeUpdate(query, user.getUserID(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public boolean deleteUser(String userID) {
        String query = "DELETE FROM users WHERE userID = ?";
        return executeUpdate(query, userID);
    }

    public User getUser(String identifier) {
        String query = "SELECT * FROM users WHERE username = ? OR userID = ?";
        return executeQueryForObject(query, identifier, identifier);
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        return executeQueryForList(query);
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE userID = ?";
        return executeUpdate(query, user.getUsername(), user.getPassword(), user.getRole(), user.getUserID());
    }

    public boolean updatePassword(String userID, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE userID = ?";
        return executeUpdate(query, newPassword, userID);
    }

}