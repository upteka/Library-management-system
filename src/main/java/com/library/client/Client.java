package main.java.com.library.client;

import main.java.com.library.client.service.ClientService;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            // 发送和接收请求的示例调用
            sendRequest(outputStream, inputStream, ClientService.AuthRequest());
            ResponsePack<?> authResponse = receiveResponse(inputStream);
            if (authResponse != null) {
                LOGGER.info("Auth response received: {}", authResponse);

                // 使用 authResponse 进行后续的业务处理
                String jwtToken = authResponse.getJwtToken();

                sendRequest(outputStream, inputStream, ClientService.AddBookRequest(jwtToken));
                ResponsePack<?> bookResponse = receiveResponse(inputStream);
                if (bookResponse != null) {
                    LOGGER.info("Book response received: {}", bookResponse);
                } else {
                    LOGGER.error("Invalid book response received");
                }
            } else {
                LOGGER.error("Invalid auth response received");
            }

        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Error occurred", e);
        }
    }

    private static void sendRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream, RequestPack<?> request) throws IOException {
        LOGGER.info("Sending request: {}", request);
        outputStream.writeObject(request);
        outputStream.flush();
    }

    private static ResponsePack<?> receiveResponse(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (ResponsePack<?>) inputStream.readObject();
    }
}