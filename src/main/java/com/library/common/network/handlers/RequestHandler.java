package main.java.com.library.common.network.handlers;

import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;

/**
 * @author PC
 */
public class RequestHandler {

    public static <T> RequestPack<T> packRequest(String action, T data, String message, String jwtToken) {
        return new RequestPack<>(action, data, message, jwtToken);
    }

    public static <T> T unPackRequest(RequestPack<T> requestPack) {
        return requestPack.getData();
    }


    public static Boolean validateRequest(RequestPack<?> requestPack) {
        String jwtToken = requestPack.getJwtToken();
        if (jwtToken == null) {
            return false;
        } else return !JwtUtil.isTokenExpired(jwtToken);
    }

    public static <T> ResponsePack<T> makeResponse(RequestPack<T> requestPack, T data, String message, boolean isSuccess) {
        return new ResponsePack<>(requestPack.getAction(), message, data, isSuccess, requestPack.getJwtToken());
    }


}