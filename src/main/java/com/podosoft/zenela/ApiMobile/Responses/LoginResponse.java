package com.podosoft.zenela.ApiMobile.Responses;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.User;

import java.util.ArrayList;
import java.util.Collection;

public class LoginResponse {
    private String body;
    private String status;
    private User principal;
    private int postNotifications;
    private int friendRequestsNotification;

    public LoginResponse() {
    }

    public LoginResponse(String body, String status) {
        this.body = body;
        this.status = status;
    }

    public LoginResponse(String body, String status, User principal) {
        this.body = body;
        this.status = status;
        this.principal = principal;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }

    public int getPostNotifications() {
        return postNotifications;
    }

    public void setPostNotifications(int postNotifications) {
        this.postNotifications = postNotifications;
    }

    public int getFriendRequestsNotification() {
        return friendRequestsNotification;
    }

    public void setFriendRequestsNotification(int friendRequestsNotification) {
        this.friendRequestsNotification = friendRequestsNotification;
    }
}
