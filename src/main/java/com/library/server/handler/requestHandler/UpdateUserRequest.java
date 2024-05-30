
package main.java.com.library.server.handler.requestHandler;

import main.java.com.library.server.model.User;

import java.io.Serializable;


public class UpdateUserRequest implements Serializable {
    private User user;

    public UpdateUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}