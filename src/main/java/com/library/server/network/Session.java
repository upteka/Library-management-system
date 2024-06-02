package main.java.com.library.server.network;

import main.java.com.library.server.entity.impl.User;

import java.io.Serial;
import java.io.Serializable;

public class Session implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String sessionId;
    private final long creationTime;
    private long lastAccessedTime;
    private User user; // Add user information

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}