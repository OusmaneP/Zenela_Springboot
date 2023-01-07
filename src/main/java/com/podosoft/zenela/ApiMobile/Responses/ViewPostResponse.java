package com.podosoft.zenela.ApiMobile.Responses;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.User;

public class ViewPostResponse {
    Post post;
    User principal;

    public ViewPostResponse() {
    }

    public ViewPostResponse(Post post, User principal) {
        this.post = post;
        this.principal = principal;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }
}
