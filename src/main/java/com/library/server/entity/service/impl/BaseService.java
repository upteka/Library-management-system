package main.java.com.library.server.entity.service.impl;

import main.java.com.library.server.database.Dao;
import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.entity.Service;

import java.util.List;

public class BaseService<T extends Entity> implements Service<T> {
    private final Dao<T> dao;

    public BaseService(Dao<T> dao) {
        this.dao = dao;
    }

    @Override
    public boolean add(T entity) {
        return dao.add(entity);
    }

    @Override
    public boolean delete(String entityId) {
        return dao.delete(entityId);
    }

    @Override
    public T get(String entityId) {
        return dao.get(entityId);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean update(T entity) {
        return dao.update(entity);
    }

    @Override
    public T getByField(String field, String name) {
        // 实现获取特定字段值的方法
        return dao.getByField(field, name);
    }
}