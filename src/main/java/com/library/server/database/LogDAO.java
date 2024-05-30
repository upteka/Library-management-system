package main.java.com.library.server.database;

import main.java.com.library.server.model.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    public boolean addLog(Log log) {
        String query = "INSERT INTO logs (logID, userID, action, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, log.getLogID());
            stmt.setString(2, log.getUserID());
            stmt.setString(3, log.getAction());
            stmt.setTimestamp(4, log.getTimestamp());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<>();
        String query = "SELECT * FROM logs";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                logs.add(new Log(
                        rs.getString("logID"),
                        rs.getString("userID"),
                        rs.getString("action"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}