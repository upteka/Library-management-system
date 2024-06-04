package main.java.com.library.server.requests;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;

import java.io.Serializable;

/**
 * 该接口定义了处理各种类型请求的契约。
 * 实现该接口的类需要是可序列化的。
 *
 * @author PC
 */
public interface Request<T> extends Serializable {

    /**
     * 返回与此请求相关的操作。
     *
     * @return 表示要执行的操作的字符串
     */
    String getAction();

    /**
     * 处理请求并返回响应对象。
     *
     * @param requestPack 包含请求数据的对象
     * @return 包含响应数据的对象
     */
    ResponsePack<T> handle(RequestPack<? extends Entity> requestPack);

//    ResponsePack<List<T>> handle(RequestPack<T> requestPack);
}