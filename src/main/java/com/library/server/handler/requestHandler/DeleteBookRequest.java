package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

// DeleteBookRequest
public class DeleteBookRequest implements Serializable {
    private String bookId;

    public DeleteBookRequest(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }
}
