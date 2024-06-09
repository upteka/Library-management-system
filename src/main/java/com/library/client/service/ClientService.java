package main.java.com.library.client.service;


import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.handlers.RequestHelper;

/**
 * 演示客户端服务如下操作：
 * 1. 创建对象, 如User、Book等, 并设置属性值
 * 2. 使用RequestHelper类创建请求包，并定义RequestPack对象, RequestPack对象包含请求的类型、请求的对象、请求的服务名、请求的JWT Token
 * 3. 发送请求包到服务端
 * 4. 接收服务端返回的响应包，并解析响应包中的结果
 * 5. 处理结果，如打印、保存等
 * 注意：本示例仅供参考，实际开发中请根据实际情况进行修改
 */
public class ClientService {
    public static RequestPack<User> AuthRequest() {
        // 创建用户对象
        User user = new User("11222211", "testPassword", "admin", "222233@ex2", "232222");
        // 创建请求包
        return RequestHelper.packRequest("auth", user, "auth", "");
    }

    public static RequestPack<Book> AddBookRequest(String jwtToken) {
        // 创建图书对象
        Book book = new Book("123456789", "a book", "1232312312", 100, "introduction", "publisher");
        // 创建图书请求包
        return RequestHelper.packRequest("add", book, "add", jwtToken);
    }

}
