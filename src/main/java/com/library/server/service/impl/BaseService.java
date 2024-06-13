package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.server.database.Dao;
import main.java.com.library.server.service.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BaseService<T extends Entity> implements Service<T> {
    private final Dao<T> dao;

    public BaseService(Dao<T> dao) {
        this.dao = dao;
    }

    @Override
    public String add(T entity) throws SQLException {
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
    public String update(T entity) {
        return dao.update(entity);
    }

    @Override
    public T getByField(String field, String name) {
        return dao.getByField(field, name);
    }

    @Override
    public List<T> search(String fieldName, Object value, String condition, int limit) {
        return dao.search(fieldName, value, condition, limit);
    }

    @Override
    public List<T> search(String fieldName, Object value, String condition, int limit, String sortField, String sortOrder, int page, int pageSize, boolean caseInsensitive, String logicalOperator, String[] selectedFields, boolean distinct, Timestamp startDate, Timestamp endDate, String dateField, Class<?> customEntityType) {
        return dao.search(fieldName, value, condition, limit, sortField, sortOrder, page, pageSize, caseInsensitive, logicalOperator, selectedFields, distinct, startDate, endDate, dateField, customEntityType);
    }
}