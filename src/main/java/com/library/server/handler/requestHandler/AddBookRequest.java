package main.java.com.library.server.handler.requestHandler;

import main.java.com.library.server.model.Book;

import java.io.Serializable;

// AddBookRequest
public class AddBookRequest implements Serializable {
    private Book book;

    public AddBookRequest(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
