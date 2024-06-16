package main.java.com.library.server.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 通用服务接口，定义基本的 CRUD 操作。
 *
 * @param <T> 实体类型
 */
public interface Service<T> {

    /**
     * 添加一个实体。
     *
     * @param entity 要添加的实体
     * @return 如果添加成功则返回 true，否则返回 false
     */
    String add(T entity) throws SQLException;

    /**
     * 根据 ID 删除一个实体。
     *
     * @param entityId 要删除的实体 ID
     * @return 如果删除成功则返回 Success，否则返回 Failed+原因
     */
    String delete(String entityId);

    /**
     * 根据 ID 获取一个实体。
     *
     * @param entityId 要获取的实体 ID
     * @return 获取到的实体，如果没有找到则返回 null
     */
    T get(String entityId);

    /**
     * 获取所有实体的列表。
     *
     * @return 所有实体的列表
     */
    List<T> getAll();

    /**
     * 更新一个实体。
     *
     * @param entity 要更新的实体
     * @return 如果更新成功则返回 true，否则返回 false
     */
    String update(T entity);

    /**
     * 使用特定的字段和值从数据库中检索实体(精确匹配)。
     *
     * @param fieldName 要搜索的字段名称
     * @param value     要搜索的字段值
     * @return 匹配指定字段和值的实体，如果不存在则返回 null
     */
    T getByField(String fieldName, String value);

    /**
     * 使用特定的字段和值从数据库中检索实体(模糊匹配)。
     *
     * @param fieldName 要搜索的字段名称
     * @param value     要搜索的字段值
     * @param condition 搜索条件，如：LIKE、=、<、>等
     * @param limit     限制返回结果的数量
     * @return 匹配指定字段和值的实体列表
     */

    List<T> search(String fieldName, Object value, String condition, int limit);

    /**
     * 搜索方法，根据指定字段、值、条件等进行查询，并返回结果列表。
     *
     * @param fieldName        要查询的字段名。
     * @param value            查询的值，如果为"0"则使用默认值。
     * @param condition        查询条件（如"=", "LIKE"等）。
     * @param limit            返回结果的限制条数，为 0 时不限制。
     * @param sortField        排序字段名。
     * @param sortOrder        排序顺序（"ASC"或"DESC"）。
     * @param page             页码，从 1 开始，默认值为 1。
     * @param pageSize         每页的记录数，默认值为 10。
     * @param caseInsensitive  是否忽略大小写，默认值为 false。
     * @param logicalOperator  自定义条件连接符（如"AND"、"OR"），默认值为"AND"。
     * @param selectedFields   返回的字段数组，默认值为 null（选择所有字段）。
     * @param distinct         是否去重，默认值为 false。
     * @param startDate        时间范围查询的开始时间，默认值为 null。
     * @param endDate          时间范围查询的结束时间，默认值为 null。
     * @param dateField        用于时间范围查询的字段，默认值为 null。
     * @param customEntityType 自定义搜索的实体类型，默认值为 null（使用默认实体类型）。
     * @return 查询结果列表。
     */
    List<T> search(String fieldName, Object value, String condition, int limit, String sortField, String sortOrder, int page, int pageSize, boolean caseInsensitive, String logicalOperator, String[] selectedFields, boolean distinct, Timestamp startDate, Timestamp endDate, String dateField, Class<?> customEntityType);
}