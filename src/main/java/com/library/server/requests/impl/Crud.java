package main.java.com.library.server.requests.impl;

import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.network.ResponsePack;
import main.java.com.library.server.requests.RequestHandler;
import main.java.com.library.server.service.impl.BaseService;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crud<T extends Entity> implements RequestHandler {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Crud.class.getName());

    private final String action; // "add", "get", "update", "delete", "list"
    private final T data;
    private final String id;
    private BaseService<T> baseService;

    public Crud(String action, T data, String id) {
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
    public ResponsePack handle() {
        try {
            Object result;
            boolean success = false;
            switch (action.toLowerCase()) {
                case "add" -> {
                    success = baseService.add(data);
                    result = success ? "Create successful" : "Create failed";
                }
                case "get" -> {
                    result = baseService.get(id);
                    success = result != null;
                }
                case "update" -> {
                    success = baseService.update(data);
                    result = success ? "Update successful" : "Update failed";
                }
                case "delete" -> {
                    success = baseService.delete(id);
                    result = success ? "Delete successful" : "Delete failed";
                }
                case "list" -> {
                    result = baseService.getAll();
                    success = result != null;
                }
                default -> throw new IllegalArgumentException("Unknown action: " + action);
            }
            String message = success ? action + " successful" : action + " failed";
            return new ResponsePack(action, message, result, success);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling action: " + action, e);
            return new ResponsePack(action, "Error handling action: " + action + ". " + e.getMessage(), null, false);
        }
    }


    public T getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}