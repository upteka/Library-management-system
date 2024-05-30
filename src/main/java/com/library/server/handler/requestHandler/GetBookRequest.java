package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public class GetBookRequest implements Serializable {
    private String identifier;

    public GetBookRequest(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
