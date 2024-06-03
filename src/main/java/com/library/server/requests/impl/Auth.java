package main.java.com.library.server.requests.impl;

import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.entity.impl.User;
import main.java.com.library.server.network.JwtUtil;
import main.java.com.library.server.network.RequestPack;
import main.java.com.library.server.network.ResponsePack;
import main.java.com.library.server.network.handlers.RequestHandler;
import main.java.com.library.server.network.handlers.ResponseHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类实现了 {@link Request} 接口，以处理用户认证请求。
 * 它支持基于用户名和密码的用户登录操作。
 *
 * @author PC
 */
public class Auth implements Request<User> {

    private final String action = "auth";
    private static final Logger logger = LoggerFactory.getLogger(Auth.class);
    private final UserService userService;

    // 通过构造函数注入依赖
    public Auth(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getAction() {
        return action;
    }

    /**
     * 处理认证请求并返回响应对象。
     * 如果用户名和密码验证成功，则创建一个新的会话并返回成功的响应包；
     * 否则，返回失败的响应包。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含操作结果的 {@link ResponsePack} 对象
     */
    @Override
    public ResponsePack<User> handle(RequestPack<? extends Entity> requestPack) {
        try {
            Entity entity = RequestHandler.unPackRequest(requestPack);
            if (!(entity instanceof User)) {
                return ResponseHandler.packResponse(action, false, "Invalid request type, expected User", null);
            }
            User user = (User) entity;
            String username = user.getUsername();
            String password = user.getPassword();

            if (userService.validateUser(username, password)) {
                user = userService.getUserByEmailOrUsername(username);
                String jwtToken = JwtUtil.generateToken(user.getUsername(), user.getRole());
                return ResponseHandler.packResponse(action, true, "success", null, jwtToken);
            } else {
                return ResponseHandler.packResponse(action, false, "Invalid username or password", null);
            }
        } catch (Exception e) {
            logger.error("Authentication failed for user: " + requestPack, e);
            return ResponseHandler.packResponse(action, false, "Internal server error", null);
        }
    }
}