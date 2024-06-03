package main.java.com.library.server.entity.impl;

import main.java.com.library.server.entity.Entity;

import java.util.Date;

public class ReturnRecord implements Entity {
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

    @Override
    public String toString() {
        return "ReturnRecord{" +
                "returnID='" + returnID + '\'' +
                ", recordID='" + recordID + '\'' +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public String getId() {
        return returnID;
    }
}