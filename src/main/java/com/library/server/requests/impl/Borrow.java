package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHelper;
import main.java.com.library.common.network.handlers.ResponseHelper;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BorrowRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


/**
 * @author upteka
 */
public class Borrow implements Request<BorrowRecord> {
    private static final Logger logger = LoggerFactory.getLogger(Borrow.class);
    private final BorrowRecordService borrowRecordService;

    public Borrow(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = new BorrowRecordService();
    }
    final String action = "borrow";

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public ResponsePack<BorrowRecord> handle(RequestPack<? extends Entity> requestPack) throws SQLException {
        Entity entity = RequestHelper.unPackRequest(requestPack);

        if (!(entity instanceof BorrowRecord borrowRecord)) {
            logger.error("Invalid request type, expected BorrowRecord");
            return ResponseHelper.packResponse(action, false, "Invalid request type, expected BorrowRecord", null);
        }
        String message = borrowRecordService.borrowBook(borrowRecord);
        boolean success = message.startsWith("Success");

        return ResponseHelper.packResponse(action, success, message, borrowRecordService.get(borrowRecord.getId()));

    }
}
