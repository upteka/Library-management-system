package main.java.com.library.server.handler.requestHandler;

import main.java.com.library.server.model.Book;

import java.io.Serializable;

// AddBookRequest
public record AddBookRequest(Book book) implements Serializable {
}
