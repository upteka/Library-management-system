package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public class GetBorrowRecordRequest implements Serializable {
    private String recordID;

    public GetBorrowRecordRequest(String recordID) {
        this.recordID = recordID;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }
}
