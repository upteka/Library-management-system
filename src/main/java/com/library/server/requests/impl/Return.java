package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.ReturnRecord;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHandler;
import main.java.com.library.common.network.handlers.ResponseHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.ReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Return implements Request<ReturnRecord> {
    private static final Logger logger = LoggerFactory.getLogger(Return.class);
    private final String action = "return";
    private final ReturnService returnService = new ReturnService(); // 直接创建 ReturnService 实例

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
     * 处理请求并返回响应对象。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含响应数据的对象
     */
    @Override
    public ResponsePack<ReturnRecord> handle(RequestPack<? extends Entity> requestPack) {
        try {
            Entity entity = RequestHandler.unPackRequest(requestPack);
            if (!(entity instanceof ReturnRecord returnRecord)) {
                return ResponseHandler.packResponse(action, false, "Invalid request type, expected ReturnRecord", null);
            }
            // 调用 ReturnService 的 returnBook 方法处理还书请求
            String result = returnService.returnBook(returnRecord);
            if (result.startsWith("Success")) {
                return ResponseHandler.packResponse(action, true, result, returnRecord); // 成功时返回还书记录
            } else {
                return ResponseHandler.packResponse(action, false, result, null);
            }
        } catch (Exception e) {
            logger.error("Return book failed for request: {}", requestPack, e);
            return ResponseHandler.packResponse(action, false, "Internal server error", null);
        }
    }
}
