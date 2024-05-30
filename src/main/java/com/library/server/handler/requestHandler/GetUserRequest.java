package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public class GetUserRequest implements Serializable {
    private String identifier;

    public GetUserRequest(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}