package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.ReturnRecord;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHelper;
import main.java.com.library.common.network.handlers.ResponseHelper;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.ReturnRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Return implements Request<ReturnRecord> {
    private static final Logger logger = LoggerFactory.getLogger(Return.class);
    private final String action = "return";
    private final ReturnRecordService returnRecordService; // 直接创建 ReturnService 实例

    public Return(ReturnRecordService returnRecordService) {
        this.returnRecordService = returnRecordService;
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
     * 处理请求并返回响应对象。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含响应数据的数据包对象
     */
    @Override
    public ResponsePack<ReturnRecord> handle(RequestPack<? extends Entity> requestPack) {
        try {
            Entity entity = RequestHelper.unPackRequest(requestPack);
            if (!(entity instanceof ReturnRecord returnRecord)) {
                return ResponseHelper.packResponse(action, false, "Invalid request type, expected ReturnRecord", null);
            }
            // 调用 ReturnService 的 returnBook 方法处理还书请求
            String result = returnRecordService.returnBook(returnRecord);
            if (result.contains("success")) {
                return ResponseHelper.packResponse(action, true, result, returnRecord); // 成功时返回还书记录
            } else {
                return ResponseHelper.packResponse(action, false, result, null);
            }
        } catch (Exception e) {
            logger.error("Return book failed for request: {}", requestPack, e);
            return ResponseHelper.packResponse(action, false, "Internal server error", null);
        }
    }
}
