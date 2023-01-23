package com.podosoft.zenela.ApiMobile.Responses;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.PostNotification;
import com.podosoft.zenela.Models.User;

import java.util.ArrayList;
import java.util.Collection;

public class PostNotificationResponse {
    private String body;
    private String status;
    private Collection<PostNotification> postNotifications = new ArrayList<>();

    public PostNotificationResponse(String body, String status) {
        this.body = body;
        this.status = status;
    }

    public PostNotificationResponse(String body, String status, Collection<PostNotification> postNotifications) {
        this.body = body;
        this.status = status;
        this.postNotifications = postNotifications;
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

    public Collection<PostNotification> getPostNotifications() {
        return postNotifications;
    }

    public void setPostNotifications(Collection<PostNotification> postNotifications) {
        this.postNotifications = postNotifications;
    }


}
