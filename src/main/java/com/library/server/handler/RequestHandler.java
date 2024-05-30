package main.java.com.library.server.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestHandler {
    private final Map<Class<?>, Function<?, String>> handlers;

    public RequestHandler() {
        handlers = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T, R> void registerHandler(Class<T> requestType, Function<T, R> handler) {
        handlers.put(requestType, (Function<T, String>) handler);
    }
    @SuppressWarnings("unchecked")
    public String handleRequest(Object request) {
        Function<Object, String> handler = (Function<Object, String>) handlers.get(request.getClass());
        if (handler != null) {
            return handler.apply(request);
        }
        return "Unknown request type: " + request.getClass();
    }
}