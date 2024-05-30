package main.java.com.library.server.handler.requestHandler;

import main.java.com.library.server.model.User;

import java.io.Serializable;

public class RegisterUserRequest implements Serializable {
    private User user;

    public RegisterUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}