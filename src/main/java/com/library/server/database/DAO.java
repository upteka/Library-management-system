package main.java.com.library.server.database;

import main.java.com.library.server.model.Entity;

import java.util.List;

public interface DAO<T extends Entity> {
    boolean add(T entity);

    boolean delete(String id);

    T get(String id);

    List<T> getAll();

    boolean update(T entity);

    T getByField(String fieldName, Object value);
}