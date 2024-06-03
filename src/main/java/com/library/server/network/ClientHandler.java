package main.java.com.library.server.network;

import main.java.com.library.server.entity.Entity;
import main.java.com.library.server.network.handlers.ResponseHandler;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.requests.impl.Crud;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 处理客户端请求的类
 */
public class ClientHandler implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private static final String REQUEST_PACKAGE = "main.java.com.library.server.requests.impl";
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            while (true) {
                try {
                    // 读取客户端发送的请求对象
                    Object request = inputStream.readObject();

                    // 检查 request 是否为 RequestPack 类型
                    if (!(request instanceof RequestPack)) {
                        LOGGER.warning("Received an invalid request object");
                        continue;
                    }

                    RequestPack<? extends Entity> requestPack = (RequestPack<? extends Entity>) request;
                    String action = requestPack.getAction();
                    LOGGER.info("Received request: " + action);

                    // 验证 token 是否有效，除了登录操作
                    if (JwtUtil.isTokenExpired(requestPack.getJwtToken()) && !"login".equals(action)) {
                        ResponsePack<?> response = ResponseHandler.packResponse(action, false, "VALID_TOKEN_REQUIRED", null);
                        outputStream.writeObject(response);
                        outputStream.flush();
                        continue;
                    }
                    if (action.equals("add") || action.equals("update") || action.equals("delete") || action.equals("get")) {
                        outputStream.writeObject(new Crud(action).handle(requestPack));
                        outputStream.flush();
                        continue;
                    }

                    // 处理请求并获取响应对象
                    ResponsePack<?> response = handleRequest(action, requestPack);

                    // 将响应对象发送给客户端
                    outputStream.writeObject(response);
                    outputStream.flush();
                } catch (ClassNotFoundException | IOException e) {
                    LOGGER.log(Level.SEVERE, "Error handling client request", e);
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error setting up streams", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Close socket error", e);
            }
        }
    }

    private ResponsePack<?> handleRequest(String action, RequestPack<? extends Entity> requestPack) {
        try {
            // 使用反射根据 action 实例化相应的请求处理类
            String requestClassName = REQUEST_PACKAGE + "." + capitalizeFirstLetter(action);
            Request<?> request = (Request<?>) Class.forName(requestClassName).getDeclaredConstructor().newInstance();
            return request.handle(requestPack);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            return new ResponsePack<>(action, "Error processing request: " + e.getMessage(), null, false);
        }
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}