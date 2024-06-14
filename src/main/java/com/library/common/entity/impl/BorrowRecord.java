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
    private boolean returned;

    // 无参数构造器
    public BorrowRecord() {
        this.borrowID = UlidCreator.getUlid().toString();
        this.borrowDate = Ulid.getInstant(borrowID);
        this.userID = null;
        this.bookID = null;
        this.returnDate = null;
        this.returned = false;
    }

    // 公共构造器
    public BorrowRecord(String userID, String bookID, Instant returnDate, boolean Returned) {
        this.borrowID = UlidCreator.getUlid().toString();
        this.borrowDate = Ulid.getInstant(borrowID);
        this.userID = userID;
        this.bookID = bookID;
        this.returnDate = returnDate;
        this.returned = Returned;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
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

    @Override
    public String getPrimaryKeyName() {
        return "borrowID";
    }
}