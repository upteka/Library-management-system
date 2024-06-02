package main.java.com.library.server.requests.impl;

import main.java.com.library.server.entity.impl.User;
import main.java.com.library.server.network.ResponsePack;
import main.java.com.library.server.network.Session;
import main.java.com.library.server.network.SessionManager;
import main.java.com.library.server.requests.RequestHandler;
import main.java.com.library.server.service.impl.UserService;

public class Auth implements RequestHandler {

    private final String username;
    private final String password;
    private final String action = "auth";
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
        return action;
    }


    @Override
    public Object handle() {
        if (userService.validateUser(username, password)) {
            Session session = SessionManager.createSession();
            User user = userService.getUserByEmaiOrUsername(username);
            session.setUser(user);
            return new ResponsePack.Builder(action)
                    .isSuccess(true)
                    .setSessionID(session.getSessionId())
                    .build();
        } else {
            return ResponsePack.failure(action, "Invalid username or password");
        }
    }


}
