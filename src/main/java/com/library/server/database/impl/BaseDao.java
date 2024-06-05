package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.server.database.Dao;
import main.java.com.library.server.database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author upteka
 */
public class BaseDao<T extends Entity> implements Dao<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class.getName());
    private final Class<T> type;

    public BaseDao(Class<T> type) {
        this.type = type;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        LOGGER.debug("Setting parameters for PreparedStatement");
        for (int i = 0; i < params.length; i++) {
            LOGGER.debug("Parameter [{}]: {}", i + 1, params[i]);
            if (params[i] instanceof Instant) {
                stmt.setTimestamp(i + 1, Timestamp.from((Instant) params[i]));
            } else {
                stmt.setObject(i + 1, params[i]);
            }
        }
    }

    private T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            T entity = constructor.newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = field.getName();
                Class<?> fieldType = field.getType();
                Object value = null;
                if (fieldType == long.class || fieldType == Long.class) {
                    value = rs.getLong(columnName);
                } else if (fieldType == int.class || fieldType == Integer.class) {
                    value = rs.getInt(columnName);
                } else if (fieldType == Instant.class) {
                    Timestamp timestamp = rs.getTimestamp(columnName);
                    if (timestamp != null) {
                        value = timestamp.toInstant();
                    }
                } else {
                    value = rs.getObject(columnName);
                }
                LOGGER.debug("Mapping column [{}] to field [{}]: {}", columnName, field.getName(), value);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            LOGGER.error("Error mapping ResultSet to entity", e);
            throw new SQLException("Error mapping ResultSet to entity", e);
        }
    }

    protected boolean executeUpdate(String query, Object... params) {
        LOGGER.info("Executing update: {}", query);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            setParameters(stmt, params);
            int affectedRows = stmt.executeUpdate();
            LOGGER.debug("Update affected {} rows", affectedRows);
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.error("SQL execution error:", e);
        }
        return false;
    }

    protected T executeQueryForObject(String query, Object... params) {
        LOGGER.info("Executing query for object: {}", query);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            LOGGER.debug("Query returned {} rows", rs.getRow());
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL execution error:", e);
        }
        return null;
    }

    protected List<T> executeQueryForList(String query, Object... params) {
        LOGGER.info("Executing query for list: {}", query);
        List<T> results = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL execution error:", e);
        }
        return results;
    }

    @Override
    public String add(T entity) throws SQLException {
        String query = "INSERT INTO " + type.getSimpleName().toLowerCase() + "s" + " (" + getFields() + ") VALUES (" + getPlaceholders() + ")";
        LOGGER.info("Executing query to add entity: {}", query);
        return executeUpdate(query, getFieldValues(entity)) ? "Success" : "Failed";
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM " + type.getSimpleName().toLowerCase() + "s WHERE " + getPrimaryKeyName() + " = ?";
        LOGGER.info("Executing query to delete entity with id: {}", id);
        return executeUpdate(query, id);
    }

    @Override
    public T get(String id) {
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s WHERE " + getPrimaryKeyName() + " = ?";
        LOGGER.info("Executing query to get entity with id: {}", id);
        return executeQueryForObject(query, id);
    }

    @Override
    public List<T> getAll() {
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s";
        LOGGER.info("Executing query to get all entities");
        return executeQueryForList(query);
    }

    @Override
    public String update(T entity) {
        String query = "UPDATE " + type.getSimpleName().toLowerCase() + "s SET " + getUpdateFields() + " WHERE " + getPrimaryKeyName() + " = ?";
        LOGGER.info("Executing query to update entity: {}", query);
        return executeUpdate(query, getFieldValuesWithId(entity)) ? "Success" : "Failed";
    }

    @Override
    public T getByField(String fieldName, Object value) {
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s WHERE " + fieldName + " = ?";
        LOGGER.info("Executing query to get entity by field [{}] with value [{}]: {}", fieldName, value, query);
        return executeQueryForObject(query, value);
    }

    private String getFields() {
        StringBuilder fields = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            fields.append(field.getName()).append(", ");
        }
        String fieldsString = fields.substring(0, fields.length() - 2);
        LOGGER.debug("Generated fields string: {}", fieldsString);
        return fieldsString;
    }

    private String getPlaceholders() {
        StringBuilder placeholders = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            placeholders.append("?, ");
        }
        String placeholdersString = placeholders.substring(0, placeholders.length() - 2);
        LOGGER.debug("Generated placeholders string: {}", placeholdersString);
        return placeholdersString;
    }

    private String getUpdateFields() {
        StringBuilder updateFields = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            if (!field.getName().equals(getPrimaryKeyName())) {
                updateFields.append(field.getName()).append(" = ?, ");
            }
        }
        String updateFieldsString = updateFields.substring(0, updateFields.length() - 2);
        LOGGER.debug("Generated update fields string: {}", updateFieldsString);
        return updateFieldsString;
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
                LOGGER.debug("Field [{}] value: {}", field.getName(), value);
            }
            return values.toArray();
        } catch (IllegalAccessException e) {
            LOGGER.error("Error getting field values", e);
            throw new RuntimeException("Error getting field values", e);
        }
    }

    private Object[] getFieldValuesWithId(T entity) {
        try {
            List<Object> values = new ArrayList<>();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals(getPrimaryKeyName())) {
                    Object value = field.get(entity);
                    if (value instanceof Instant) {
                        values.add(Timestamp.from((Instant) value));
                    } else {
                        values.add(value);
                    }
                    LOGGER.debug("Field [{}] value: {}", field.getName(), value);
                }
            }
            Field idField = type.getDeclaredField(getPrimaryKeyName());
            idField.setAccessible(true);
            Object idValue = idField.get(entity);
            values.add(idValue);
            LOGGER.debug("Primary key [{}] value: {}", idField.getName(), idValue);
            return values.toArray();
        } catch (Exception e) {
            LOGGER.error("Error getting field values with id", e);
            throw new RuntimeException("Error getting field values with id", e);
        }
    }

    private String getPrimaryKeyName() {
        try {
            return type.getDeclaredConstructor().newInstance().getPrimaryKeyName();
        } catch (Exception e) {
            LOGGER.error("Error getting primary key name", e);
            throw new RuntimeException("Error getting primary key name", e);
        }
    }
}