package main.java.com.library.server.entity.impl;

import main.java.com.library.server.entity.Entity;

public class Book implements Entity {
    private final String bookID;
    private String title;
    private String author;
    private String ISBN;
    private String status; // "available" or "borrowed"

    // Constructor, getters, setters, and toString() omitted for brevity

    public Book(String bookID, String title, String author, String ISBN, String status) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
    }

    public String getBookID() {
        return bookID;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return bookID;
    }
}