package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.impl.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class.getName());

    public UserDao(Class<User> userClass) {
        super(User.class);
    }

    @Override
    public List<User> search(String fieldName, Object value, String condition, int limit, String sortField, String sortOrder, int page, int pageSize, boolean caseInsensitive, String logicalOperator, String[] selectedFields, boolean distinct, Timestamp startDate, Timestamp endDate, String dateField, Class<?> customEntityType) {
        // 设置默认值
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 10;
        if (logicalOperator == null || logicalOperator.isEmpty()) logicalOperator = "AND";
        if (value == null || "0".equals(value)) value = ""; // 默认值为空字符串

        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        if (distinct) {
            queryBuilder.append("DISTINCT ");
        }

        if (selectedFields != null && selectedFields.length > 0) {
            queryBuilder.append(String.join(", ", selectedFields));
        } else {
            queryBuilder.append("*");
        }

        queryBuilder.append(" FROM ").append(customEntityType != null ? customEntityType.getSimpleName() : type.getSimpleName()).append("s WHERE ");

        // 排除 Deleted 字段为 true 的用户
        queryBuilder.append("Deleted = false AND ");

        if (caseInsensitive) {
            queryBuilder.append("LOWER(").append(fieldName).append(") ");
            value = value.toString().toLowerCase();
        } else {
            queryBuilder.append(fieldName).append(" ");
        }

        if ("LIKE".equalsIgnoreCase(condition)) {
            queryBuilder.append("LIKE ?");
            value = "%" + value + "%";
        } else {
            queryBuilder.append(condition).append(" ?");
        }

        if (startDate != null && endDate != null && dateField != null && !dateField.isEmpty()) {
            queryBuilder.append(" AND ").append(dateField).append(" BETWEEN ? AND ?");
        }

        if (sortField != null && !sortField.isEmpty()) {
            queryBuilder.append(" ORDER BY ").append(sortField).append(" ").append((sortOrder != null && sortOrder.equalsIgnoreCase("DESC")) ? "DESC" : "ASC");
        }

        int offset = (page - 1) * pageSize;
        queryBuilder.append(" LIMIT ").append(pageSize).append(" OFFSET ").append(offset);

        String query = queryBuilder.toString();
        LOGGER.info("Executing search with field [{}], condition [{}], value [{}], limit [{}], sort field [{}], sort order [{}], page [{}], page size [{}], caseInsensitive [{}], logicalOperator [{}], selectedFields [{}], distinct [{}], startDate [{}], endDate [{}], dateField [{}], customEntityType [{}]: {}",
                fieldName, condition, value, limit, sortField, sortOrder, page, pageSize, caseInsensitive, logicalOperator, selectedFields, distinct, startDate, endDate, dateField, customEntityType, query);

        List<Object> params = new ArrayList<>();
        params.add(value);
        if (startDate != null && endDate != null) {
            params.add(startDate);
            params.add(endDate);
        }

        return executeQueryForList(query, params.toArray());
    }
}

