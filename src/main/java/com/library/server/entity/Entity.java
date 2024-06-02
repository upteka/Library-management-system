package main.java.com.library.server.entity;

import java.io.Serializable;

public interface Entity extends Serializable {
    String getId();

    String toString();
}