package com.podosoft.zenela.Dto;

public class Notifications {
    private Long messages;
    private Long posts;
    private Long friendship;

    public Notifications() {
    }

    public Notifications(Long messages, Long posts, Long friendship) {
        this.messages = messages;
        this.posts = posts;
        this.friendship = friendship;
    }

    public Long getMessages() {
        return messages;
    }

    public void setMessages(Long messages) {
        this.messages = messages;
    }

    public Long getPosts() {
        return posts;
    }

    public void setPosts(Long posts) {
        this.posts = posts;
    }

    public Long getFriendship() {
        return friendship;
    }

    public void setFriendship(Long friendship) {
        this.friendship = friendship;
    }
}
