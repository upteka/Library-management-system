package main.java.com.library.server.model;

import java.io.Serializable;
import java.util.Date;

public class ReturnRecord implements Serializable {
    private String returnID;
    private String recordID;
    private Date returnDate;

    public ReturnRecord(String returnID, String recordID, Date returnDate) {
        this.returnID = returnID;
        this.recordID = recordID;
        this.returnDate = returnDate;
    }

    public String getReturnID() {
        return returnID;
    }

    public void setReturnID(String returnID) {
        this.returnID = returnID;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}