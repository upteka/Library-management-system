package main.java.com.library.server.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

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
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object handleRequest(Object request) {
        if (request instanceof Request) {
            Request baseRequest = (Request) request;
            return baseRequest.handle();
        }
        return null; // 或者返回适当的错误信息
    }
}