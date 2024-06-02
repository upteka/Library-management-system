package main.java.com.library.server.requests.impl;

import main.java.com.library.server.requests.RequestHandler;

import java.io.Serial;
import java.io.Serializable;

public abstract class Request implements Serializable, RequestHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public abstract String getAction();

    public abstract Object handle();
}