package main.java.com.library.server.service;

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
    String add(T entity);

    /**
     * 根据 ID 删除一个实体。
     *
     * @param entityId 要删除的实体 ID
     * @return 如果删除成功则返回 true，否则返回 false
     */
    boolean delete(String entityId);

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

}