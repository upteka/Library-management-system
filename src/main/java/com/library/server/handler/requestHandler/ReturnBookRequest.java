package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public record ReturnBookRequest(String recordID) implements Serializable {
}