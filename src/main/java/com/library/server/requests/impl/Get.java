package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.EntityList;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BaseService;

import java.util.List;

public class Get<T extends Entity> implements Request<T> {
    private final String action = "get";
    private final BaseService<T> baseService;

    public Get(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    @Override
    public String getAction() {
        return action;
    }

    /**
     * 处理请求并返回响应对象。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含响应数据的对象
     */
    @Override
    public ResponsePack<T> handle(RequestPack<? extends Entity> requestPack) {
        String getMessage = requestPack.getMessage();
        try {
            Message msg = new Message(getMessage);
            if (!msg.isValid()) {
                return new ResponsePack<>(action, "无效的请求参数", null, false);
            }
            return handleGetMessage(msg);
        } catch (Exception e) {
            return new ResponsePack<>(action, "处理请求时出错: " + e.getMessage(), null, false);
        }
    }

    private ResponsePack<T> handleGetMessage(Message msg) {
        if ("all".equalsIgnoreCase(msg.action)) {
            return new ResponsePack<>(action, "获取所有实体成功", (T) new EntityList<>(baseService.getAll()), true);
        } else if ("search".equalsIgnoreCase(msg.action)) {
            return searchEntities(msg.fieldName, msg.value, msg.condition, msg.limit);
        } else {
            return new ResponsePack<>(action, "无效的请求参数", null, false);
        }
    }

    private ResponsePack<T> searchEntities(String fieldName, String value, String condition, int limit) {
        List<T> entities = baseService.search(fieldName, value, condition, limit);
        if (entities != null && !entities.isEmpty()) {
            return new ResponsePack<>(action, "获取实体成功", (T) new EntityList<>(entities), true);
        } else {
            return new ResponsePack<>(action, "未找到符合条件的实体", null, false);
        }
    }

    private Integer parseLimit(String limitStr) {
        try {
            return Integer.parseInt(limitStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    class Message {
        public String action;
        public String fieldName;
        public String value;
        public String condition;
        public Integer limit;

        public Message(String message) {
            String[] params = message.split(",");
            if (params.length != 5) {
                return;
            }
            action = params[0].trim();
            fieldName = params[1].trim();
            value = params[2].trim();
            condition = params[3].trim().toUpperCase();
            limit = parseLimit(params[4].trim());
        }

        public boolean isValid() {
            return action != null && fieldName != null && value != null && condition != null && limit != null;
        }
    }
}
