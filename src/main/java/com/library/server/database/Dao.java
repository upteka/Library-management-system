package main.java.com.library.server.database;

import main.java.com.library.common.entity.Entity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 数据访问对象（DAO）接口，提供实体的 CRUD（创建、读取、更新、删除）操作。
 *
 * @param <T> 扩展自 {@link Entity} 的实体类型
 */
public interface Dao<T extends Entity> {

    /**
     * 将新的实体添加到数据库。
     *
     * @param entity 要添加的实体
     * @return 如果实体添加成功，则返回 true；否则返回 false
     */
    String add(T entity) throws SQLException;

    /**
     * 使用实体的 ID 从数据库中删除实体。
     *
     * @param id 要删除的实体的 ID
     * @return 如果实体删除成功，则返回 true；否则返回 false
     */
    String delete(String id);

    /**
     * 使用实体的 ID 从数据库中检索实体。
     *
     * @param id 要检索的实体的 ID
     * @return 具有指定 ID 的实体，如果不存在则返回 null
     */
    T get(String id);

    /**
     * 使用实体的 ID 从数据库中检索实体。
     *
     * @param limit 检索实体的数量
     * @return 具有指定 ID 的实体的列表，如果不存在则返回 null
     */
    List<T> getAll(int limit);

    /**
     * 从数据库中检索所有实体。
     *
     * @return 所有实体的列表, 如果不存在则返回 null
     */
    List<T> getAll();

    /**
     * 更新数据库中的现有实体。
     *
     * @param entity 包含更新值的实体
     * @return 如果实体更新成功，则返回 "Success"；否则返回 "Failed"
     */
    String update(T entity);

    /**
     * 使用特定的字段和值从数据库中检索实体(精确匹配)。
     *
     * @param fieldName 要搜索的字段名称
     * @param value     要搜索的字段值
     * @return 匹配指定字段和值的实体，如果不存在则返回 null
     */
    T getByField(String fieldName, Object value);


    // 新增的 search 方法

    /**
     * 搜索方法，根据指定字段、值、条件等进行查询，并返回结果列表。
     *
     * @param fieldName       要查询的字段名。
     * @param value           查询的值，如果为"0"则使用默认值。
     * @param condition       查询条件（如"=", "LIKE"等）。
     * @param limit           返回结果的限制条数，为 0 时不限制。
     * @param sortField       排序字段名。
     * @param sortOrder       排序顺序（"ASC"或"DESC"）。
     * @param page            页码，从 1 开始，默认值为 1。
     * @param pageSize        每页的记录数，默认值为 10。
     * @param caseInsensitive 是否忽略大小写，默认值为 false。
     * @param logicalOperator 自定义条件连接符（如"AND"、"OR"），默认值为"AND"。
     * @param selectedFields  返回的字段数组，默认值为 null（选择所有字段）。
     * @param distinct        是否去重，默认值为 false。
     * @param startDate       时间范围查询的开始时间，默认值为 null。
     * @param endDate         时间范围查询的结束时间，默认值为 null。
     * @param dateField       用于时间范围查询的字段，默认值为 null。
     * @param customEntityType 自定义搜索的实体类型，默认值为 null（使用默认实体类型）。
     * @return 查询结果列表。
     */

    List<T> search(String fieldName, Object value, String condition, int limit, String sortField, String sortOrder, int page, int pageSize, boolean caseInsensitive, String logicalOperator, String[] selectedFields, boolean distinct, Timestamp startDate, Timestamp endDate, String dateField, Class<?> customEntityType);
    /**
     * 搜索方法，根据指定字段、值、条件等进行查询，并返回结果列表。
     *
     * @param fieldName       要查询的字段名。
     * @param value           查询的值，如果为"0"则使用默认值。
     * @param condition       查询条件（如"=", "LIKE"等）。
     * @param limit           返回结果的限制条数，为 0 时不限制。
     * @return 查询结果列表。
     */
    List<T> search(String fieldName, Object value, String condition, int limit);
}