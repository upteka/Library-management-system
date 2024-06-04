package main.java.com.library.common.entity;

import java.io.Serializable;

/**
 * 表示实体对象的接口。
 *
 * <p>
 * 实体对象必须具有唯一标识符。
 * </p>
 *
 * <p>
 * 实体对象必须能够提供其唯一标识符的方法。
 * </p>
 *
 * <p>
 * 实体对象应该覆盖 toString() 方法以提供有用的信息。
 * </p>
 *
 * @author PC
 */
public interface Entity extends Serializable {
    /**
     * 获取实体对象的唯一标识符。
     *
     * @return 实体对象的唯一标识符
     */
    String getId();

    /**
     * 返回实体对象的字符串表示。
     *
     * @return 实体对象的字符串表示
     */
    @Override
    String toString();
}
