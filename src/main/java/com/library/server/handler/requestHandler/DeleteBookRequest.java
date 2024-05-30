package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

// DeleteBookRequest
public record DeleteBookRequest(String bookId) implements Serializable {
}
