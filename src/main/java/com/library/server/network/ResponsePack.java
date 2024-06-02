package main.java.com.library.server.network;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ResponsePack {
    private String action;
    private Object data;
    private String message;
    private boolean isSuccess;
    private String sessionID;

    // 私有化构造函数
    private ResponsePack(Builder builder) {
        this.action = builder.action;
        this.data = builder.data;
        this.message = builder.message;
        this.isSuccess = builder.isSuccess;
        this.sessionID = builder.sessionID;
    }

    // 公共构造函数
    public ResponsePack(String action, String message, Object data, boolean isSuccess, String sessionID) {
        this.action = action;
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
        this.sessionID = sessionID;
    }

    //无sessionID构造函数
    public ResponsePack(String action, String message, Object data, boolean isSuccess) {
        this.action = action;
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
        this.sessionID = "null";
    }

    public static ResponsePack success(String action, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public String toString() {
        return "ResponsePack [action=" + action + ", data=" + data.toString() + ", message=" + message + ", isSuccess=" + isSuccess + ", sessionID=" + sessionID + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsePack that = (ResponsePack) o;
        return isSuccess == that.isSuccess && Objects.equals(action, that.action) && Objects.equals(data, that.data) && Objects.equals(message, that.message) && Objects.equals(sessionID, that.sessionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, data, message, isSuccess, sessionID);
    }

    // Builder Class
    public static class Builder {
        private final String action;
        private Object data = null;
        private String message = "";
        private boolean isSuccess = false;
        private String sessionID = "null";

        public Builder(@NotNull String action) {
            if (action == null || action.isEmpty()) {
                throw new IllegalArgumentException("Action cannot be null or empty");
            }
            this.action = action;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder message(@NotNull String message) {
            if (message == null || message.isEmpty()) {
                throw new IllegalArgumentException("Message cannot be null or empty");
            }
            this.message = message;
            return this;
        }

        public Builder isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public Builder setSessionID(@NotNull String sessionID) {
            if (sessionID == null || sessionID.isEmpty()) {
                throw new IllegalArgumentException("SessionID cannot be null or empty");
            }
            this.sessionID = sessionID;
            return this;
        }

        public ResponsePack build() {
            return new ResponsePack(this);
        }
    }
}