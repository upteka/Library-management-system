package main.java.com.library.server.handler.impl;

import main.java.com.library.server.database.impl.BaseDAO;
import main.java.com.library.server.handler.Request;
import main.java.com.library.server.model.Entity;

import java.io.Serial;

public class CRUDRequest<T extends Entity> extends Request {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String action; // "add", "get", "update", "delete", "list"
    private final T data;
    private final String id;
    private final BaseDAO<T> dao;

    public CRUDRequest(String action, T data, String id, BaseDAO<T> dao) {
        this.action = action;
        this.data = data;
        this.id = id;
        this.dao = dao;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public Object handle() {
        switch (action.toLowerCase()) {
            case "add":
                return dao.add(data) ? "Create successful" : "Create failed";
            case "get":
                return dao.get(id);
            case "update":
                return dao.update(data) ? "Update successful" : "Update failed";
            case "delete":
                return dao.delete(id) ? "Delete successful" : "Delete failed";
            case "list":
                return dao.getAll();
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    public T getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}