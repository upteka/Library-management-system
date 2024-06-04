package main.java.com.library.common.entity.impl;

import main.java.com.library.common.entity.Entity;

import java.util.UUID;

/**
 * 书籍实体类。
 * 包含书籍的基本信息，如书籍ID、标题、作者、ISBN号、数量、简介、出版社等。
 * 书籍ID是基于书籍信息的 Hash UUID。
 */
public class Book implements Entity {
    private final String bookID;
    private String title;
    private String author;
    private String ISBN;
    private String status; // "available" or "borrowed"
    private Integer count;
    private String introduction;
    private String publisher;
    private Integer availableCount;


    /**
     * 构造函数用于初始化书籍对象。
     *
     * @param title        标题
     * @param author       作者
     * @param ISBN         ISBN号
     * @param count        数量
     * @param introduction 简介
     * @param publisher    出版社
     */

    public Book(String title, String author, String ISBN, Integer count, String introduction, String publisher) {
        this.bookID = generateBookID(title, author, ISBN, publisher);
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.count = count;
        this.introduction = introduction;
        this.publisher = publisher;

    }

    private String generateBookID(String title, String author, String ISBN, String publisher) {
        // 生成基于书籍信息的 Hash UUID
        String combinedString = title + author + ISBN + publisher;
        return UUID.nameUUIDFromBytes(combinedString.getBytes()).toString();
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

    private void setStatus(String status) {
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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



