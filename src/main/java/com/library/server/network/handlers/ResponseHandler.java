package main.java.com.library.server.network.handlers;

import main.java.com.library.server.network.ResponsePack;

/**
 * @author PC
 */
public class ResponseHandler {

    public static <T> ResponsePack<T> packResponse(String action, boolean isSuccess, String message, T data, String JwtToken) {
        return new ResponsePack(action, message, data, isSuccess, JwtToken);
    }

    public static <T> ResponsePack<T> packResponse(String action, boolean isSuccess, String message, T data) {
        return new ResponsePack(action, message, data, isSuccess);
    }
}

