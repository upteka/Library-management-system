package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.sql.SQLException;

/**
 * 该类实现了 {@link Request} 接口，以处理 CRUD 操作。
 * 它支持对实体执行「添加」、「获取(通过 id)」、「更新」、「删除(通过 id)」等操作。
 * ---仅限管理员和用户(自己)有权限进行操作。
 * @param <T> 实体的类型
 */
public class Crud<T extends Entity> implements Request<T> {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Crud.class);

    private final String action;
    private final BaseService<T> service;

    /**
     * 构造一个新的 Crud 请求处理器。
     *
     * @param action 要执行的操作
     * @param entityType 实体类型
     */
    public Crud(String action, String entityType) {
        this.action = action;
        this.service = getServiceForEntity(entityType);
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
     * @return 包含操作结果的 {@link ResponsePack} 对象,
     * ResponsePack.getData() 返回操作的对象,ResponsePack.isSuccess() 返回操作是否成功,
     * ResponsePack.getMessage() 返回操作的结果信息,ResponsePack.getType() 返回操作的实体类型
     * ResponsePack.getJwtToken() 返回 JWT 令牌,必须在请求中带上
     * @throws IllegalArgumentException 如果请求的实体类型无法找到对应的服务类
     */
    @Override
    public ResponsePack<T> handle(RequestPack<? extends Entity> requestPack) {
        try {
            String entityName = requestPack.getType();
            @SuppressWarnings("unchecked")
            T data = (T) requestPack.getData();
            String id = data != null ? data.getId() : null;
            String jwtToken = requestPack.getJwtToken();

            // 权限检查
            if (!hasPermission(entityName, data, jwtToken)) {
                return new ResponsePack<>(action, "Insufficient permissions to perform the operation", null, false);
            }

            LOGGER.info("Processing {} action for entity: {}", action, entityName);
            return processAction(action, entityName, data, id, jwtToken);
        } catch (Exception e) {
            LOGGER.error("Error processing action: {}", action, e);
            return new ResponsePack<>(action, "Error processing action: " + e.getMessage(), null, false);
        }
    }

    /**
     * 检查用户权限
     */
    private boolean hasPermission(String entityName, T data, String jwtToken) {
        boolean isUser = data instanceof User;
        boolean isAdmin = JwtUtil.isaAdmin(jwtToken);
        boolean isTokenOwner = data != null && JwtUtil.isTokenOwner(jwtToken, data.getId());

        return switch (action.toLowerCase()) {
            case "add" -> isAdmin;
            case "update", "delete" -> (isTokenOwner && isUser) || isAdmin;
            case "get" -> true;
            default -> false;
        };
    }

    /**
     * 处理 CRUD 操作
     */
    protected ResponsePack<T> processAction(String action, String entityName, T data, String id, String jwtToken) throws SQLException {
        boolean success;
        String message;
        T result = null;

        switch (action.toLowerCase()) {
            case "add":
                message = service.add(data);
                success = message.contains("Success");
                break;
            case "update":
                message = service.update(data);
                success = message.startsWith("Success");
                break;
            case "delete":
                success = service.delete(id).contains("Success");
                message = service.delete(id);
                break;
            case "get":
                result = service.get(id);
                success = result != null;
                message = success ? entityName + " get successfully" : entityName + " get failed";
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
        return new ResponsePack<>(action, message, result, success);
    }

    /**
     * 根据实体类型获取相应的服务类
     */
    @SuppressWarnings("unchecked")
    private BaseService<T> getServiceForEntity(String entityType) {
        try {
            String serviceClassName = "main.java.com.library.server.service.impl." + entityType + "Service";
            Class<?> serviceClass = Class.forName(serviceClassName);
            return (BaseService<T>) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LOGGER.error("Failed to instantiate service class {}", entityType, e);
            throw new IllegalArgumentException("Failed to instantiate service class: " + e.getMessage(), e);
        }
    }
}