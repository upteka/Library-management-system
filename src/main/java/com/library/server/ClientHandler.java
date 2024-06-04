package main.java.com.library.server;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.ResponseHandler;
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
                    Object request = inputStream.readObject();

                    if (!(request instanceof RequestPack<?>) || !((RequestPack<?>) request).isEntity()) {
                        sendInvalidRequestResponse(outputStream);
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                    RequestPack<? extends Entity> requestPack = (RequestPack<? extends Entity>) request;
                    String action = requestPack.getAction();
                    LOGGER.info("Received request: " + action);

                    if (JwtUtil.isTokenExpired(requestPack.getJwtToken()) && !"login".equals(action)) {
                        sendResponse(outputStream, ResponseHandler.packResponse(action, false, "Valid token required, please login again", null));
                        continue;
                    }

                    if (isCrudAction(action)) {
                        sendResponse(outputStream, new Crud<>(action).handle(requestPack));
                    } else {
                        sendResponse(outputStream, handleRequest(action, requestPack));
                    }

                } catch (ClassNotFoundException | IOException e) {
                    LOGGER.log(Level.SEVERE, "Error handling client request", e);
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error setting up streams", e);
        } finally {
            closeSocket();
        }
    }

    private boolean isCrudAction(String action) {
        return "add".equals(action) || "update".equals(action) || "delete".equals(action) || "get".equals(action);
    }

    private void sendInvalidRequestResponse(ObjectOutputStream outputStream) throws IOException {
        LOGGER.warning("Received an invalid request object");
        sendResponse(outputStream, ResponseHandler.packResponse("N/A", false, "INVALID_REQUEST_TYPE", null));
    }

    private void sendResponse(ObjectOutputStream outputStream, ResponsePack<?> response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }

    private void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Close socket error", e);
        }
    }

    private ResponsePack<?> handleRequest(String action, RequestPack<? extends Entity> requestPack) {
        try {
            String requestClassName = REQUEST_PACKAGE + "." + capitalizeFirstLetter(action);
            Class<?> requestClass = Class.forName(requestClassName);

            if (!Request.class.isAssignableFrom(requestClass)) {
                throw new ClassNotFoundException("Class " + requestClassName + " does not implement Request interface");
            }

            Request<?> request = (Request<?>) requestClass.getDeclaredConstructor().newInstance();
            return request.handle(requestPack);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "No handler found for action: " + action, e);
            return ResponseHandler.packResponse(action, false, "Undefined action: " + action, null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            return ResponseHandler.packResponse(action, false, "Error processing request: " + e.getMessage(), null);
        }
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}