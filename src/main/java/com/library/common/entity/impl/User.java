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

    public User() {
        this.userID = UlidCreator.getUlid().toString();
        this.username = null;
        this.password = null;
        this.email = null;
        this.role = null;
    }

    public User(String username, String password, String role, String email, String phone) {
        if ((email == null || email.isEmpty()) && (phone == null || phone.isEmpty())) {
            throw new IllegalArgumentException("Either email or phone must be provided.");
        }
        this.userID = UlidCreator.getUlid().toString();
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public User(String username, String password, String role, String contact) {
        if (contact.isEmpty()) {
            throw new IllegalArgumentException("Either email or phone must be provided.");
        }
        this.userID = UlidCreator.getUlid().toString();
        this.username = username;
        this.password = password;
        this.role = role;
        if (contact.contains("@")) {
            this.email = contact;
            this.phone = "";
        } else {
            this.phone = contact;
            this.email = "";
        }
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
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return userID;
    }
}