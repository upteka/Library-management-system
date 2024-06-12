package main.java.com.library.common.network.handlers;

import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.RequestPack;

/**
 * @author PC
 */
public class RequestHelper {

    public static <T> RequestPack<T> packRequest(String action, T data, String message, String jwtToken, String... params) {
        return new RequestPack<>(action, data, message, jwtToken, params);
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



}