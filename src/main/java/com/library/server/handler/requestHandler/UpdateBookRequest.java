package main.java.com.library.server.handler.requestHandler;

import main.java.com.library.server.model.Book;

import java.io.Serializable;

public class UpdateBookRequest implements Serializable {
    private Book book;

    public UpdateBookRequest(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
