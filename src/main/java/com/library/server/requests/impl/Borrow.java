package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHandler;
import main.java.com.library.common.network.handlers.ResponseHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BorrowService;

/**
 * @author upteka
 */
public class Borrow implements Request<BorrowRecord> {


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

        if (!(entity instanceof BorrowRecord)) {
            return ResponseHandler.packResponse(action, false, "Invalid request type, expected BorrowRecord", null, jwtToken);
        }

        if (borrowService.borrowBook((BorrowRecord) requestPack.getData())) {
            return ResponseHandler.packResponse(action, true, "Borrowed successfully", null, jwtToken);
        }

        return ResponseHandler.packResponse(action, false, "Borrowed failed the book is not available", null, jwtToken);
    }
}
