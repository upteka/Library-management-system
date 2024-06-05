package main.java.com.library.client;

import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;

import java.io.Serializable;

/**
 * 该接口定义了客户端处理请求和响应的契约。
 * 实现该接口的类需要是可序列化的。
 *
 * @param <T> 请求数据类型
 */
public interface ClientRequest<T> extends Serializable {

    /**
     * 创建请求包。
     *
     * @param action   表示要执行的操作的字符串
     * @param data     请求数据, 能为 null(最好不要为 null)
     * @param jwtToken 用于身份验证的 JWT 令牌
     * @return 包含请求数据的对象
     */
    RequestPack<T> makeRequest(String action, T data, String jwtToken);

    /**
     * 处理响应并返回结果。
     *
     * @param responsePack 包含响应数据的对象
     * @return 处理后的数据对象
     */
    T handleResponse(ResponsePack<T> responsePack);
}