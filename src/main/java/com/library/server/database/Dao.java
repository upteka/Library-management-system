package main.java.com.library.server.database;

import main.java.com.library.server.entity.Entity;

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
    boolean add(T entity);

    /**
     * 使用实体的 ID 从数据库中删除实体。
     *
     * @param id 要删除的实体的 ID
     * @return 如果实体删除成功，则返回 true；否则返回 false
     */
    boolean delete(String id);

    /**
     * 使用实体的 ID 从数据库中检索实体。
     *
     * @param id 要检索的实体的 ID
     * @return 具有指定 ID 的实体，如果不存在则返回 null
     */
    T get(String id);

    /**
     * 从数据库中检索所有实体。
     *
     * @return 所有实体的列表
     */
    List<T> getAll();

    /**
     * 更新数据库中的现有实体。
     *
     * @param entity 包含更新值的实体
     * @return 如果实体更新成功，则返回 true；否则返回 false
     */
    boolean update(T entity);

    /**
     * 使用特定的字段和值从数据库中检索实体(精确匹配)。
     *
     * @param fieldName 要搜索的字段名称
     * @param value     要搜索的字段值
     * @return 匹配指定字段和值的实体，如果不存在则返回 null
     */
    T getByField(String fieldName, Object value);
}