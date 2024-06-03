package main.java.com.library.server.network;

import java.util.Objects;

/**
 * @author PC
 */
public class RequestPack<T> {

    private String action;
    private T data;
    private String message;
    private String JwtToken = "";

    public RequestPack(String action, T data, String message, String JwtToken) {
        this.action = action;
        this.data = data;
        this.message = message;
        this.JwtToken = JwtToken;
    }

    public RequestPack(String action, T data, String message) {
        this(action, data, message, "");
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getJwtToken() {
        return JwtToken;
    }

    public void setJwtToken(String JwtToken) {
        this.JwtToken = JwtToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestPack [action=" + action + ", data=" + data + ", token=" + JwtToken + ", message=" + message + "]";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestPack<?> that = (RequestPack<?>) o;
        return Objects.equals(action, that.action) && Objects.equals(data, that.data) && Objects.equals(JwtToken, that.JwtToken) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, data, JwtToken, message);
    }

    static class RequestPackBuilder<T> {
        private String action;
        private T data;
        private String JwtToken;
        private String message;

        public RequestPackBuilder<T> setAction(String action) {
            this.action = action;
            return this;
        }

        public RequestPackBuilder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public RequestPackBuilder<T> setJwtToken(String JwtToken) {
            this.JwtToken = JwtToken;
            return this;
        }

        public RequestPackBuilder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public RequestPack<T> build() {
            return new RequestPack<>(action, data, message, JwtToken);
        }
    }
}