package main.java.com.library.server.network;

import java.util.Objects;

public class RequestPack {

    private String action;
    private Object data;
    private String message;
    private String sessionID;

    public RequestPack(String action, Object data, String message, String sessionID) {
        this.action = action;
        this.data = data;
        this.sessionID = sessionID;
        this.message = message;
    }

    public RequestPack(String action, Object data, String message) {
        this.action = action;
        this.data = data;
        this.sessionID = "";
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "RequestPack [action=" + action + ", data=" + data.toString() + ", token=" + sessionID + ", message=" + message + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPack that = (RequestPack) o;
        return Objects.equals(action, that.action) && Objects.equals(data, that.data) && Objects.equals(sessionID, that.sessionID) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, data, sessionID, message);
    }

    class RequestPackBuilder {
        private String action;
        private Object data;
        private String sessionID;
        private String message;

        public RequestPackBuilder setAction(String action) {
            this.action = action;
            return this;
        }

        public RequestPackBuilder setData(Object data) {
            this.data = data;
            return this;
        }

        public RequestPackBuilder setSessionID(String sessionID) {
            this.sessionID = sessionID;
            return this;
        }

        public RequestPackBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public RequestPack build() {
            return new RequestPack(action, data, sessionID, message);
        }


    }


}