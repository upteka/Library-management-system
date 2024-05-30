package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public class ReturnBookRequest implements Serializable {
    private String recordID;

    public ReturnBookRequest(String recordID) {
        this.recordID = recordID;
    }

    public String getRecordID() {
        return recordID;
    }
}