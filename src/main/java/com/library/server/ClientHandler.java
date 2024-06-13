package main.java.com.library.server;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.FavoriteRecord;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.ResponseHelper;
import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.requests.impl.Crud;
import main.java.com.library.server.service.impl.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.net.Socket;


/**
 * 处理客户端请求的类
 */
public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);
    private static final String REQUEST_PACKAGE = "main.java.com.library.server.requests.impl";
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            while (!clientSocket.isClosed()) {
                try {
                    Object request = inputStream.readObject();
                    LOGGER.info("Received request object of type: " + request.getClass().getName());

                    if (!(request instanceof RequestPack<?>) || !((RequestPack<?>) request).isEntity()) {
                        sendInvalidRequestResponse(outputStream);
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                    RequestPack<? extends Entity> requestPack = (RequestPack<? extends Entity>) request;
                    LOGGER.info("Received request pack: " + requestPack);
                    String action = requestPack.getAction();
                    LOGGER.info("Processing action: " + action);

                    if ((requestPack.getJwtToken() == null || requestPack.getJwtToken().isEmpty()) && !"auth".equals(action) && !"register".equals(action)) {
                        sendResponse(outputStream, ResponseHelper.packResponse(action, false, "Valid token required, please login again", null));
                        continue;
                    }

                    if (JwtUtil.isTokenExpired(requestPack.getJwtToken()) && !"auth".equals(action) && !"register".equals(action)) {
                        sendResponse(outputStream, ResponseHelper.packResponse(action, false, "Token expired, please login again", null));
                        continue;
                    }

                    if (isCrudAction(action)) {
                        sendResponse(outputStream, new Crud<>(action, requestPack.getType()).handle(requestPack), requestPack.getJwtToken());
                    } else if (requestPack.getJwtToken().isEmpty() && ("auth".equals(action) || "register".equals(action))) {
                        sendResponse(outputStream, handleRequest(action, requestPack));
                    } else {
                        sendResponse(outputStream, handleRequest(action, requestPack), requestPack.getJwtToken());
                    }

                } catch (EOFException e) {
                    LOGGER.info("Client closed connection");
                    break; // Exit the loop if the client has closed the connection
                } catch (ClassNotFoundException | IOException e) {
                    LOGGER.error("Error handling client request", e);
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error setting up streams", e);
        } finally {
            closeSocket();
        }
    }

    private boolean isCrudAction(String action) {
        return "add".equals(action) || "update".equals(action) || "delete".equals(action) || "get".equals(action);
    }

    private void sendInvalidRequestResponse(ObjectOutputStream outputStream) throws IOException {
        LOGGER.warn("Received an invalid request object");
        sendResponse(outputStream, ResponseHelper.packResponse("N/A", false, "INVALID_REQUEST_TYPE", null));
    }

    private void sendResponse(ObjectOutputStream outputStream, ResponsePack<?> response) throws IOException {
        LOGGER.info("Sending response: " + response.toString());
        outputStream.writeObject(response);
        outputStream.flush();
    }

    private void sendResponse(ObjectOutputStream outputStream, ResponsePack<?> response, String JwtToken) throws IOException {
        LOGGER.info("Sending response: " + response.toString());
        response.setJwtToken(JwtToken);
        outputStream.writeObject(response);
        outputStream.flush();
    }

    private void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error("Close socket error", e);
        }
    }

    private ResponsePack<?> handleRequest(String action, RequestPack<? extends Entity> requestPack) {
        try {
            String requestClassName = REQUEST_PACKAGE + "." + capitalizeFirstLetter(action);
            Class<?> requestClass = Class.forName(requestClassName);

            if (!Request.class.isAssignableFrom(requestClass)) {
                throw new ClassNotFoundException("Class " + requestClassName + " does not implement Request interface");
            }

            // 动态获取服务类
            String entityType = requestPack.getType();
            BaseService<? extends Entity> service = null;

            if (!"FavoriteRecord".equals(entityType)) {
                service = getServiceForEntity(entityType);
            }

            // 找到合适的构造函数
            Constructor<?> constructor;
            Request<?> request;
            if ("FavoriteRecord".equals(entityType)) {
                constructor = requestClass.getConstructor(BaseDao.class);
                BaseDao<FavoriteRecord> favoriteDao = new BaseDao<>(FavoriteRecord.class);
                request = (Request<?>) constructor.newInstance(favoriteDao);
            } else {
                constructor = getConstructor(requestClass, service.getClass());
                request = (Request<?>) constructor.newInstance(service);
            }

            return request.handle(requestPack);
        } catch (ClassNotFoundException e) {
            LOGGER.warn("No handler found for action: {}", action, e);
            return ResponseHelper.packResponse(action, false, "Undefined action: " + action, null);
        } catch (Exception e) {
            LOGGER.warn("Error processing request", e);
            return ResponseHelper.packResponse(action, false, "Error processing request: " + e.getMessage(), null);
        }
    }

    private Constructor<?> getConstructor(Class<?> requestClass, Class<?> serviceClass) throws NoSuchMethodException {
        for (Constructor<?> constructor : requestClass.getConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(serviceClass)) {
                return constructor;
            }
        }
        throw new NoSuchMethodException("No suitable constructor found for " + requestClass.getName());
    }

    private BaseService<? extends Entity> getServiceForEntity(String entityType) {
        try {
            String serviceClassName = "main.java.com.library.server.service.impl." + entityType + "Service";
            Class<?> serviceClass = Class.forName(serviceClassName);
            return (BaseService<? extends Entity>) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LOGGER.error("Failed to instantiate service class {}", entityType, e);
            throw new IllegalArgumentException("Failed to instantiate service class: " + e.getMessage(), e);
        }
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
