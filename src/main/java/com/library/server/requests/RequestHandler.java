package main.java.com.library.server.requests;

import java.io.Serializable;

public interface RequestHandler extends Serializable {
    String getAction();

    Object handle();
}
