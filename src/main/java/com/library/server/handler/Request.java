package main.java.com.library.server.handler;

import java.io.Serial;
import java.io.Serializable;

public abstract class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public abstract String getAction();

    public abstract Object handle();
}