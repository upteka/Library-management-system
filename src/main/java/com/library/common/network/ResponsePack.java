package main.java.com.library.common.network;

import org.jetbrains.annotations.NotNull;


/**
 * @author upteka
 */
public class ResponsePack<T> {
    private String action;
    private T data;
    private String message;
    private boolean isSuccess;
    private String JwtToken;

    // 私有化构造函数
    private ResponsePack(Builder<T> builder) {
        this.action = builder.action;
        this.data = (T) builder.data;
        this.message = builder.message;
        this.isSuccess = builder.isSuccess;
        this.JwtToken = builder.JwtToken;
    }

    // 公共构造函数
    public ResponsePack(String action, String message, T data, boolean isSuccess, String JwtToken) {
        this.action = action;
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
        this.JwtToken = JwtToken;
    }

    //无JwtToken构造函数
    public ResponsePack(String action, String message, T data, boolean isSuccess) {
        this.action = action;
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
        this.JwtToken = "null";
    }

    public ResponsePack success(String action, T data) {
        return new ResponsePack.Builder(action).data(data).message("success").isSuccess(true).build();
    }

    public static ResponsePack success(String action) {
        return new ResponsePack.Builder(action).data(null).message("success").isSuccess(true).build();
    }

    public static ResponsePack failure(String action, String message) {
        return new ResponsePack.Builder(action).message(message).isSuccess(false).build();
    }

    // Getter and Setter methods
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "ResponsePack [action=" + action + ", data=" + data.toString() + ", message=" + message + ", isSuccess=" + isSuccess + ", JwtToken=" + JwtToken + "]";
    }


    // Builder Class
    public static class Builder<T> {
        private final String action;
        private T data = null;
        private String message = "";
        private boolean isSuccess = false;
        private String JwtToken = "null";

        public Builder(@NotNull String action) {
            if (action.isEmpty()) {
                throw new IllegalArgumentException("Action cannot be null or empty");
            }
            this.action = action;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder message(@NotNull String message) {
            if (message.isEmpty()) {
                throw new IllegalArgumentException("Message cannot be null or empty");
            }
            this.message = message;
            return this;
        }

        public Builder isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public Builder setJwtToken(@NotNull String JwtToken) {
            if (JwtToken.isEmpty()) {
                throw new IllegalArgumentException("JwtToken cannot be null or empty");
            }
            this.JwtToken = JwtToken;
            return this;
        }

        public ResponsePack build() {
            return new ResponsePack(this);
        }
    }
}