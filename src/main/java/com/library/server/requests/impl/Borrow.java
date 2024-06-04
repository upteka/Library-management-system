package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHandler;
import main.java.com.library.common.network.handlers.ResponseHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BorrowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author upteka
 */
public class Borrow implements Request<BorrowRecord> {
    private static final Logger logger = LoggerFactory.getLogger(Borrow.class);


    final BorrowService borrowService = new BorrowService();
    final String action = "borrow";

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public ResponsePack<BorrowRecord> handle(RequestPack<? extends Entity> requestPack) {
        Entity entity = RequestHandler.unPackRequest(requestPack);
        String jwtToken = requestPack.getJwtToken();

        if (!(entity instanceof BorrowRecord borrowRecord)) {
            logger.error("Invalid request type, expected BorrowRecord");
            return ResponseHandler.packResponse(action, false, "Invalid request type, expected BorrowRecord", null, jwtToken);
        }

        return ResponseHandler.packResponse(action, true, borrowService.borrowBook(borrowRecord), borrowService.get(borrowRecord.getId()), jwtToken);

    }
}
