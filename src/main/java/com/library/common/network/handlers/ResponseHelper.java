package main.java.com.library.common.network.handlers;

import main.java.com.library.common.network.ResponsePack;

/**
 * @author PC
 */
public class ResponseHelper {

    public static <T> ResponsePack<T> packResponse(String action, boolean isSuccess, String message, T data, String JwtToken) {
        return new ResponsePack<>(action, message, data, isSuccess, JwtToken);
    }

    public static <T> ResponsePack<T> packResponse(String action, boolean isSuccess, String message, T data) {
        return new ResponsePack<>(action, message, data, isSuccess, "");
    }
}

