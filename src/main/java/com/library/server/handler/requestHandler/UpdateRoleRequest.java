package main.java.com.library.server.handler.requestHandler;

import java.io.Serializable;

public class UpdateRoleRequest implements Serializable {
    private String userID;
    private String newRole;

    public UpdateRoleRequest(String userID, String newRole) {
        this.userID = userID;
        this.newRole = newRole;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
}