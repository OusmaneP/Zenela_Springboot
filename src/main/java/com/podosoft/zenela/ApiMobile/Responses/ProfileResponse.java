package com.podosoft.zenela.ApiMobile.Responses;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.User;

import java.util.ArrayList;
import java.util.Collection;

public class ProfileResponse {
    private String body;
    private String status;
    private User principal;
    private Collection<User> myFriends = new ArrayList<>();
    private Collection<User> friendRequests = new ArrayList<>();
    private Collection<User> invitedFriends = new ArrayList<>();
    private Collection<Post> posts = new ArrayList<>();
    private Boolean friends = false;

    public ProfileResponse() {
    }

    public ProfileResponse(String body, String status) {
        this.body = body;
        this.status = status;
    }

    public ProfileResponse(String body, String status, User principal) {
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

    public Collection<User> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(Collection<User> myFriends) {
        this.myFriends = myFriends;
    }

    public Collection<User> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Collection<User> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public Collection<User> getInvitedFriends() {
        return invitedFriends;
    }

    public void setInvitedFriends(Collection<User> invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public Boolean getFriends() {
        return friends;
    }

    public void setFriends(Boolean friends) {
        this.friends = friends;
    }
}
