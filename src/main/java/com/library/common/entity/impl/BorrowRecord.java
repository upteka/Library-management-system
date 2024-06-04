package main.java.com.library.common.entity.impl;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import main.java.com.library.common.entity.Entity;

import java.time.Instant;

public class BorrowRecord implements Entity {
    private String borrowID;
    private String userID;
    private String bookID;
    private Instant borrowDate;
    private Instant returnDate;


    public BorrowRecord(String userID, String bookID, Instant returnDate) {
        this.borrowID = UlidCreator.getUlid().toString();
        this.userID = userID;
        this.bookID = bookID;
        this.borrowDate = Ulid.getInstant(borrowID);
        this.returnDate = returnDate;
    }

    public String getBorrowID() {
        return borrowID;
    }

    private void setBorrowID(String borrowID) {
        this.borrowID = borrowID;
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

    public Instant getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Instant borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Instant getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Instant returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "borrowID='" + borrowID + '\'' +
                ", userID='" + userID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public String getId() {
        return borrowID;
    }
}