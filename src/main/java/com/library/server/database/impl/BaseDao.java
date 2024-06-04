package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.server.database.Dao;
import main.java.com.library.server.database.DatabaseManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author upteka
 */
public class BaseDao<T extends Entity> implements Dao<T> {
    private static final Logger LOGGER = Logger.getLogger(BaseDao.class.getName());
    private final Class<T> type;

    public BaseDao(Class<T> type) {
        this.type = type;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Instant) {
                stmt.setTimestamp(i + 1, Timestamp.from((Instant) params[i]));
            } else {
                stmt.setObject(i + 1, params[i]);
            }
        }
    }

    private T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            T entity = type.getDeclaredConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = field.getName();
                Class<?> fieldType = field.getType();
                if (fieldType == long.class || fieldType == Long.class) {
                    field.set(entity, rs.getLong(columnName));
                } else if (fieldType == int.class || fieldType == Integer.class) {
                    field.set(entity, rs.getInt(columnName));
                } else if (fieldType == Instant.class) {
                    Timestamp timestamp = rs.getTimestamp(columnName);
                    if (timestamp != null) {
                        field.set(entity, timestamp.toInstant());
                    }
                } else {
                    field.set(entity, rs.getObject(columnName));
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
    public String add(T entity) throws SQLException {
        String query = "INSERT INTO " + type.getSimpleName().toLowerCase() + "s" + " (" + getFields() + ") VALUES (" + getPlaceholders() + ")";
        return executeUpdate(query, getFieldValues(entity)) ? "success" : "failed";
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
    public String update(T entity) {
        String query = "UPDATE " + type.getSimpleName().toLowerCase() + "s SET " + getUpdateFields() + " WHERE id = ?";
        return executeUpdate(query, getFieldValuesWithId(entity)) ? "success" : "failed";
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
                Object value = field.get(entity);
                if (value instanceof Instant) {
                    values.add(Timestamp.from((Instant) value));
                } else {
                    values.add(value);
                }
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
                    Object value = field.get(entity);
                    if (value instanceof Instant) {
                        values.add(Timestamp.from((Instant) value));
                    } else {
                        values.add(value);
                    }
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