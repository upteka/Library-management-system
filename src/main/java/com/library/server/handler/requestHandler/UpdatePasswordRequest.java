package main.java.com.library.server.handler.requestHandler;


import java.io.Serializable;

public class UpdatePasswordRequest implements Serializable {
    private String userID;
    private String newPassword;

    public UpdatePasswordRequest(String userID, String newPassword) {
        this.userID = userID;
        this.newPassword = newPassword;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
