package main.java.com.library.client;

import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientUtil {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientUtil.class);

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientUtil() throws IOException {
        this.socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public static void startClient() {
        try {
            ClientUtil clientUtil = new ClientUtil();
            LOGGER.info("Client started and connected to server at {}:{}", SERVER_ADDRESS, SERVER_PORT);
        } catch (IOException e) {
            LOGGER.error("Error starting client", e);
        }
    }

    public void sendRequest(RequestPack<?> request) throws IOException {
        LOGGER.info("Sending request: {}", request);
        outputStream.writeObject(request);
        outputStream.flush();
    }

    public ResponsePack<?> receiveResponse() throws IOException, ClassNotFoundException {
        ResponsePack<?> response = (ResponsePack<?>) inputStream.readObject();
        LOGGER.info("Received response: {}", response);
        return response;
    }

    public void close() {
        try {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            if (socket != null) socket.close();
            LOGGER.info("Client connection closed");
        } catch (IOException e) {
            LOGGER.error("Error closing client connection", e);
        }
    }
}