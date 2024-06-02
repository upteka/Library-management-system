package main.java.com.library.server.network;

import main.java.com.library.server.entity.impl.User;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static Session createSession() {
        String sessionId = generateSessionId();
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    private static String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public void setUser(String sessionId, User user) {
        Session session = getSession(sessionId);
        if (session != null) {
            session.setUser(user);
        }
    }
}