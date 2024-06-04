package main.java.com.library.common.entity.impl;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import main.java.com.library.common.entity.Entity;

import java.time.Instant;

public class ReturnRecord implements Entity {
    private String returnID;
    private String borrowID;
    private Instant returnDate;

    public ReturnRecord(String borrowID) {
        this.returnID = UlidCreator.getUlid().toString();
        this.borrowID = borrowID;
        this.returnDate = Ulid.getInstant(returnID);
    }

    public String getReturnID() {
        return returnID;
    }

    public void setReturnID(String returnID) {
        this.returnID = returnID;
    }

    public String getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(String borrowID) {
        this.borrowID = borrowID;
    }

    public Instant getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Instant returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "ReturnRecord{" +
                "returnID='" + returnID + '\'' +
                ", borrowID='" + borrowID + '\'' +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public String getId() {
        return returnID;
    }
}