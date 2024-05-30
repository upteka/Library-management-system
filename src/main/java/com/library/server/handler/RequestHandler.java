package main.java.com.library.server.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestHandler {
    private final Map<Class<?>, Function<?, ?>> handlers;

    public RequestHandler() {
        handlers = new HashMap<>();
    }

    public <T, R> void registerHandler(Class<T> requestType, Function<T, R> handler) {
        handlers.put(requestType, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> Object handleRequest(T request) {
        Function<T, ?> handler = (Function<T, ?>) handlers.get(request.getClass());
        if (handler != null) {
            return handler.apply(request);
        }
        return "Unknown request type: " + request.getClass();
    }
}