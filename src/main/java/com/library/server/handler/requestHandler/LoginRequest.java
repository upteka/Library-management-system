package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public record LoginRequest(String username, String password) implements Serializable {
}