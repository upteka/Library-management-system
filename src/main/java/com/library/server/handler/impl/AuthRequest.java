package main.java.com.library.server.handler.impl;

import main.java.com.library.server.handler.Request;
import main.java.com.library.server.model.User;
import main.java.com.library.server.service.UserService;

public class AuthRequest extends Request {

    private final String username;
    private final String password;
    UserService userService = new UserService();

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getAction() {
        return "auth";
    }

    @Override
    public Object handle() {
        User user = userService.getBy("username", username);
        if (user != null && user.getPassword().equals(password)) {
            return "Authentication successful";
        } else {
            return "Authentication failed";
        }
    }


}
