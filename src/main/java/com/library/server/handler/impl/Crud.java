package main.java.com.library.server.handler.impl;

import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.entity.service.impl.BaseService;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crud<T extends Entity> extends Request {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Crud.class.getName());

    private final String action; // "add", "get", "update", "delete", "list"
    private final T data;
    private final String id;
    private final BaseService<T> baseService;

    public Crud(String action, T data, String id, BaseService<T> baseService) {
        this.action = action;
        this.data = data;
        this.id = id;
        this.baseService = baseService;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public Object handle() {
        try {
            return switch (action.toLowerCase()) {
                case "add" -> baseService.add(data) ? "Create successful" : "Create failed";
                case "get" -> baseService.get(id);
                case "update" -> baseService.update(data) ? "Update successful" : "Update failed";
                case "delete" -> baseService.delete(id) ? "Delete successful" : "Delete failed";
                case "list" -> baseService.getAll();
                default -> throw new IllegalArgumentException("Unknown action: " + action);
            };
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling action: " + action, e);
            return "Error handling action: " + action + ". " + e.getMessage();
        }
    }

    public T getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}