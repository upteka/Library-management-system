package main.java.com.library.server.handler.impl;

import main.java.com.library.server.handler.Requests;

import java.io.Serial;
import java.io.Serializable;

public abstract class Request implements Serializable, Requests {
    @Serial
    private static final long serialVersionUID = 1L;

    public abstract String getAction();

    public abstract Object handle();
}