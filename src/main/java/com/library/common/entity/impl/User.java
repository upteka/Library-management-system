package main.java.com.library.common.entity.impl;

import com.github.f4b6a3.ulid.UlidCreator;
import main.java.com.library.common.entity.Entity;


public class User implements Entity {
    private String userID;
    private String username;
    private String password;
    private String role; // "admin" or "user" or "root"
    private String phone;
    private String email; // optional


    public User(String username, String password, String role, String email) {
        this.userID = UlidCreator.getUlid().toString();
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String username, String password, String role, String email, String phone) {
        this.userID = UlidCreator.getUlid().toString();
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }


    public String getUserID() {
        return userID;
    }

    private void setUserID(String userID) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String getId() {
        return userID;
    }
}