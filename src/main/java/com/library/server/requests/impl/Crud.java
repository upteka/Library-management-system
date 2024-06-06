package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
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
 * 它支持对实体执行「添加」、「获取」、「更新」、「删除」和「列出」等操作。
 *
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

            if (!checkPermissions(jwtToken, action, entityName)) {
                return new ResponsePack<>(action, "权限不足，无法执行操作", null, false);
            }
            LOGGER.info("处理 {} 操作对象: {}", action, entityName);

            return processAction(action, entityName, data, id);
        } catch (Exception e) {
            LOGGER.error("处理操作时出错: {}", action, e);
            return new ResponsePack<>(action, "处理操作时出错: " + e.getMessage(), null, false);
        }
    }

    protected boolean checkPermissions(String jwtToken, String action, String entityName) {
        return JwtUtil.canPerform(jwtToken, action, entityName);
    }

    protected ResponsePack<T> processAction(String action, String entityName, T data, String id) throws SQLException {
        boolean success = false;
        String message = "";
        T result = null;
        switch (action.toLowerCase()) {
            case "add":
                LOGGER.info("添加 {} 服务: {}", entityName, service);
                message = service.add(data);
                success = message.contains("Success");
                break;
            case "update":
                success = service.update(data).startsWith("Success");
                message = success ? entityName + " 更新成功" : entityName + " 更新失败";
                break;
            case "delete":
                success = service.delete(id);
                message = success ? entityName + " 删除成功" : entityName + " 删除失败";
                break;
            default:
                throw new IllegalArgumentException("未知的操作: " + action);
        }
        return new ResponsePack<>(action, message, result, success);
    }

    @SuppressWarnings("unchecked")
    protected BaseService<T> getServiceForEntity(String entityType) {
        try {
            String serviceClassName = "main.java.com.library.server.service.impl." + entityType + "Service";
            Class<?> serviceClass = Class.forName(serviceClassName);
            return (BaseService<T>) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LOGGER.error("无法实例化服务类 {}", entityType, e);
            throw new IllegalArgumentException("无法实例化服务类: " + e.getMessage(), e);
        }
    }
}