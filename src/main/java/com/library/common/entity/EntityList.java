package main.java.com.library.common.entity;

import java.util.List;
import java.util.stream.Collectors;

public record EntityList<T extends Entity>(List<T> entities) implements Entity {
    @Override
    public String getId() {
        return "";
    }

    @Override
    public String toString() {
        String entitiesString = entities.stream()
                .map(Entity::toString)
                .collect(Collectors.joining(", "));
        return "EntityList{entities=[" + entitiesString + "]}";
    }
}