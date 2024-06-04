package main.java.com.library.server.requests.impl;

import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;

/**
 * @author upteka
 */
public class BaseRequest<T> {

    private ResponsePack<T> responsePack;
    private RequestPack<?> requestPack;
    private String action;

    public ResponsePack<T> getResponsePack() {
        return responsePack;
    }

    public void setResponsePack(ResponsePack<T> responsePack) {
        this.responsePack = responsePack;
    }


}
