package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public class DeleteBorrowRecordRequest implements Serializable {
    private String recordID;

    public DeleteBorrowRecordRequest(String recordID) {
        this.recordID = recordID;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }
}