package main.java.com.library.server.service;

import main.java.com.library.server.database.LogDAO;
import main.java.com.library.server.model.Log;

import java.util.List;

public class LogService {
    private LogDAO logDAO;

    public LogService() {
        logDAO = new LogDAO();
    }

    public boolean addLog(Log log) {
        return logDAO.addLog(log);
    }

    public List<Log> getAllLogs() {
        return logDAO.getAllLogs();
    }
}