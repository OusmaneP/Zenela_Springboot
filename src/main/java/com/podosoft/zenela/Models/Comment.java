package com.podosoft.zenela.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commenter;

    private Long post;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    private String commenterName;

    @Transient
    private String commenterProfile;

    public Comment() {
    }

    public Comment(Long commenter, Long post, String commentText, Date createdAt) {
        this.commenter = commenter;
        this.post = post;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommenter() {
        return commenter;
    }

    public void setCommenter(Long commenter) {
        this.commenter = commenter;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterProfile() {
        return commenterProfile;
    }

    public void setCommenterProfile(String commenterProfile) {
        this.commenterProfile = commenterProfile;
    }
}
