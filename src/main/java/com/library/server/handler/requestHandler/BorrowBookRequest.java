package main.java.com.library.server.handler.requestHandler;


import java.io.Serializable;

public class BorrowBookRequest implements Serializable {
    private String recordID;
    private String userID;
    private String bookID;

    public BorrowBookRequest(String recordID, String userID, String bookID) {
        this.recordID = recordID;
        this.userID = userID;
        this.bookID = bookID;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
}