package main.java.com.library.server.entity.impl;

import main.java.com.library.server.entity.Entity;

import java.util.Date;

public class BorrowRecord implements Entity {
    private String borrowID;
    private String userID;
    private String bookID;
    private Date borrowDate;
    private Date returnDate;


    public BorrowRecord(String borrowID, String userID, String bookID, Date borrowDate, Date returnDate) {
        this.borrowID = borrowID;
        this.userID = userID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public String getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(String borrowID) {
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
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