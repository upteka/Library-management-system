package main.java.com.library.server.database.impl;

import main.java.com.library.server.database.Dao;
import main.java.com.library.server.database.DatabaseManager;
import main.java.com.library.server.entity.Entity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDao<T extends Entity> implements Dao<T> {
    private static final Logger LOGGER = Logger.getLogger(BaseDao.class.getName());
    private final Class<T> type;

    public BaseDao(Class<T> type) {
        this.type = type;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    private T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            T entity = type.getDeclaredConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType() == long.class) {
                    field.set(entity, rs.getLong(field.getName()));
                } else {
                    field.set(entity, rs.getObject(field.getName()));
                }
            }
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error mapping ResultSet to entity", e);
        }
    }

    protected boolean executeUpdate(String query, Object... params) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL execution error", e);
        }
        return false;
    }

    protected T executeQueryForObject(String query, Object... params) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL execution error", e);
        }
        return null;
    }

    protected List<T> executeQueryForList(String query, Object... params) {
        List<T> results = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL execution error", e);
        }
        return results;
    }

    @Override
    public boolean add(T entity) {
        String query = "INSERT INTO " + type.getSimpleName().toLowerCase() + "s" + " (" + getFields() + ") VALUES (" + getPlaceholders() + ")";
        return executeUpdate(query, getFieldValues(entity));
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM " + type.getSimpleName().toLowerCase() + "s WHERE id = ?";
        return executeUpdate(query, id);
    }

    @Override
    public T get(String id) {
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s WHERE id = ?";
        return executeQueryForObject(query, id);
    }

    @Override
    public List<T> getAll() {
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s";
        return executeQueryForList(query);
    }

    @Override
    public boolean update(T entity) {
        String query = "UPDATE " + type.getSimpleName().toLowerCase() + "s SET " + getUpdateFields() + " WHERE id = ?";
        return executeUpdate(query, getFieldValuesWithId(entity));
    }

    @Override
    public T getByField(String fieldName, Object value) {
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s WHERE " + fieldName + " = ?";
        return executeQueryForObject(query, value);
    }

    private String getFields() {
        StringBuilder fields = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            fields.append(field.getName()).append(", ");
        }
        return fields.substring(0, fields.length() - 2);
    }

    private String getPlaceholders() {
        StringBuilder placeholders = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            placeholders.append("?, ");
        }
        return placeholders.substring(0, placeholders.length() - 2);
    }

    private String getUpdateFields() {
        StringBuilder updateFields = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                updateFields.append(field.getName()).append(" = ?, ");
            }
        }
        return updateFields.substring(0, updateFields.length() - 2);
    }

    private Object[] getFieldValues(T entity) {
        try {
            List<Object> values = new ArrayList<>();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                values.add(field.get(entity));
            }
            return values.toArray();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error getting field values", e);
        }
    }

    private Object[] getFieldValuesWithId(T entity) {
        try {
            List<Object> values = new ArrayList<>();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    values.add(field.get(entity));
                }
            }
            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            values.add(idField.get(entity));
            return values.toArray();
        } catch (Exception e) {
            throw new RuntimeException("Error getting field values", e);
        }
    }
}