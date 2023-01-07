package com.podosoft.zenela.ApiMobile.Responses;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SearchResponse implements Serializable {
    private String body;
    private String status;
    private Collection<User> users = new ArrayList<>();

    public SearchResponse() {
    }

    public SearchResponse(String body, String status) {
        this.body = body;
        this.status = status;
    }

    public SearchResponse(String body, String status, Collection<User> users) {
        this.body = body;
        this.status = status;
        this.users = users;
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

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
