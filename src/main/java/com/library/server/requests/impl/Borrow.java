package main.java.com.library.server.requests.impl;

import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.entity.impl.BorrowRecord;
import main.java.com.library.server.network.RequestPack;
import main.java.com.library.server.network.ResponsePack;
import main.java.com.library.server.network.handlers.RequestHandler;
import main.java.com.library.server.network.handlers.ResponseHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BorrowService;

/**
 * @author PC
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
        if (!(entity instanceof BorrowRecord)) {
            return ResponseHandler.packResponse(action, false, "Invalid request type, expected BorrowRecord", null, requestPack.getJwtToken());
        }


        if (borrowService.borrowBook((BorrowRecord) requestPack.getData())) {
            return ResponseHandler.packResponse(action, true, "Borrowed successfully", null, requestPack.getJwtToken());
        }

        return ResponseHandler.packResponse(action, false, "Borrowed failed the book is not available", null, requestPack.getJwtToken());
    }
}
