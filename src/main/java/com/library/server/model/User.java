package main.java.com.library.server.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userID;
    private String username;
    private String password;
    private String role; // "admin" or "user"

    public User(String userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}