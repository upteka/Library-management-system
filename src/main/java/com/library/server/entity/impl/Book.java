package main.java.com.library.server.entity.impl;

import main.java.com.library.server.entity.Entity;

public class Book implements Entity {
    private final String bookID;
    private String title;
    private String author;
    private String ISBN;
    private String status; // "available" or "borrowed"
    private Integer count;
    private String introduction;

    /**
     * 构造函数用于初始化书籍对象。
     *
     * @param bookID       书籍ID
     * @param title        标题
     * @param author       作者
     * @param ISBN         ISBN号
     * @param status       状态
     * @param count        数量
     * @param introduction 简介
     */
    public Book(String bookID, String title, String author, String ISBN, String status, Integer count, String introduction) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.count = count;
        this.introduction = introduction;
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

    public Integer getCount() {
        return count;
    }

    public boolean isAvailable() {
        return status.equals("available");
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }



    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", status='" + status + '\'' +
                ", count=" + count +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return bookID;
    }
}