package main.java.com.library.server.requests.impl;

import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.network.JwtUtil;
import main.java.com.library.server.network.RequestPack;
import main.java.com.library.server.network.ResponsePack;
import main.java.com.library.server.network.handlers.RequestHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BaseService;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 该类实现了 {@link Request} 接口，以处理 CRUD 操作。
 * 它支持对实体执行「添加」、「获取」、「更新」、「删除」和「列出」等操作。
 *
 * @param <T> 实体的类型
 * @author PC
 */
public class Crud<T extends Entity> implements Request<T> {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Crud.class.getName());

    private final String action;
    BaseService<T> baseService;


    /**
     * 构造一个新的 Crud 请求处理器。
     *
     * @param action 要执行的操作
     */
    public Crud(String action) {
        this.action = action;
    }

    private static String getClassName(Class<?> clazz) {
        return clazz != null ? clazz.getSimpleName() : "Entity";
    }

    /**
     * 返回与此请求相关的操作。
     *
     * @return 表示要执行的操作的字符串
     */
    @Override
    public String getAction() {
        return action;
    }

    /**
     * 处理 CRUD 操作并返回包含结果的响应包。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含操作结果的 {@link ResponsePack} 对象
     */
    @Override
    public ResponsePack<T> handle(RequestPack<? extends Entity> requestPack) {
        try {
            if (!RequestHandler.validateRequest(requestPack)) {
                return new ResponsePack<>(action, "请求验证失败", null, false);
            }

            String entityName = getClassName(requestPack.getData().getClass());
            T data = (T) requestPack.getData();
            String message;
            boolean success = false;
            T result = null;
            String id = data != null ? data.getId() : null;
            String jwtToken = requestPack.getJwtToken();

            // 检查用户是否有权限执行此操作
            if (!JwtUtil.canPerform(jwtToken, action, entityName)) {
                return new ResponsePack<>(action, "权限不足，无法执行操作", null, false);
            }

            switch (action.toLowerCase()) {
                case "add":
                    success = baseService.add(data);
                    message = success ? entityName + " 创建成功" : entityName + " 创建失败";
                    break;
                case "get":
                    result = baseService.get(id);
                    success = result != null;
                    message = success ? entityName + " 获取成功" : entityName + " 获取失败";
                    break;
                case "update":
                    success = baseService.update(data);
                    message = success ? entityName + " 更新成功" : entityName + " 更新失败";
                    break;
                case "delete":
                    success = baseService.delete(id);
                    message = success ? entityName + " 删除成功" : entityName + " 删除失败";
                    break;
                default:
                    throw new IllegalArgumentException("未知的操作: " + action);
            }

            return new ResponsePack<>(action, message, result, success);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "处理操作时出错: " + action, e);
            return new ResponsePack<>(action, "处理操作时出错: " + e.getMessage(), null, false);
        }
    }
}