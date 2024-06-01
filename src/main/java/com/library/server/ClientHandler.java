package main.java.com.library.server;

import main.java.com.library.server.handler.impl.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                // 读取客户端发送的请求对象
                Object request = inputStream.readObject();

                // 处理请求并获取响应对象
                Object response = handleRequest(request);

                // 将响应对象发送给客户端
                outputStream.writeObject(response);
                outputStream.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error handling client request", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Close socket error", e);
            }
        }
    }

    private Object handleRequest(Object request) {
        if (request instanceof Request baseRequest) {
            return baseRequest.handle();
        }
        return null; // 或者返回适当的错误信息
    }
}