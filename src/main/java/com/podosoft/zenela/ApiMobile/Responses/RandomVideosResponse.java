package com.podosoft.zenela.ApiMobile.Responses;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class RandomVideosResponse implements Serializable {
    private String body;
    private String status;
    private Collection<Post> posts = new ArrayList<>();
    private User principal;

    public RandomVideosResponse(String body, String status) {
        this.body = body;
        this.status = status;
    }

    public RandomVideosResponse(String body, String status, Collection<Post> posts, User principal) {
        this.body = body;
        this.status = status;
        this.posts = posts;
        this.principal = principal;
    }

    public RandomVideosResponse(String body, String status, Collection<Post> videos) {
        this.body = body;
        this.status = status;
        this.posts = videos;
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

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }
}
