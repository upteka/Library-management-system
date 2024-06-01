package main.java.com.library.server.handler.impl;

import main.java.com.library.server.entity.impl.User;
import main.java.com.library.server.entity.service.impl.UserService;
import main.java.com.library.server.handler.Requests;

public class Auth extends Request implements Requests {

    private final String username;
    private final String password;
    UserService userService = new UserService();

    public Auth(String username, String password) {
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
        User user = userService.getByField("username", username);
        if (user != null && user.getPassword().equals(password)) {
            return "Authentication successful";
        } else {
            return "Authentication failed";
        }
    }


}
