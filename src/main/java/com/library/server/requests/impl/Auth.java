package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHelper;
import main.java.com.library.common.network.handlers.ResponseHelper;
import main.java.com.library.server.JwtUtil;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类实现了 {@link Request} 接口，以处理用户认证请求。
 * 它支持基于用户名和密码的用户登录操作。
 *
 * @author upteka
 */
public class Auth implements Request<User> {

    private final String action = "auth";
    private static final Logger logger = LoggerFactory.getLogger(Auth.class);
    private final UserService userService;

    // 通过构造函数注入依赖
    public Auth() {
        userService = new UserService();
    }


    @Override
    public String getAction() {
        return action;
    }

    /**
     * 处理认证请求并返回响应对象。
     * 如果用户名和密码验证成功，则创建一个新的会话并返回成功的响应包, 其中包含 JWT 令牌；
     * 否则，返回失败的响应包, 其中包含错误信息。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含操作结果的 {@link ResponsePack} 对象
     */
    @Override
    public ResponsePack<User> handle(RequestPack<? extends Entity> requestPack) {
        try {
            Entity entity = RequestHelper.unPackRequest(requestPack);
            if (!(entity instanceof User user)) {
                return ResponseHelper.packResponse(action, false, "Invalid request type, expected User", null);
            }
            String username = user.getUsername();
            String contact = user.getContact();
            String password = user.getPassword();

            if (userService.validateUser(username, password) || userService.validateUser(contact, password)) {
                user = userService.getUser(username);
                String jwtToken = JwtUtil.generateToken(user.getUserID(), user.getRole());
                logger.info("Authentication success for user: {}", username);
                return ResponseHelper.packResponse(action, true, "Authenticated Successfully", null, jwtToken);
            } else {
                logger.info("Authentication failed for user: {}", username);
                return ResponseHelper.packResponse(action, false, "Invalid username or password", null);
            }
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", requestPack, e);
            return ResponseHelper.packResponse(action, false, "Internal server error", null);
        }
    }
}