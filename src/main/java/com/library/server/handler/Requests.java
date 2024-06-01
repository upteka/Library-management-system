package main.java.com.library.server.handler;

import java.io.Serializable;

public interface Requests extends Serializable {
    String getAction();

    Object handle();
}
