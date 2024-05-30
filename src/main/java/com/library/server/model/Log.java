package main.java.com.library.server.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
    private String logID;
    private String userID;
    private String action;
    private Timestamp timestamp;

    public Log(String logID, String userID, String action, Timestamp timestamp) {
        this.logID = logID;
        this.userID = userID;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logID='" + logID + '\'' +
                ", userID='" + userID + '\'' +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}