package main.java.com.library.common.entity;

import java.util.List;

public record EntityList<T extends Entity>(List<T> entities) implements Entity {
    @Override
    public String getId() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
