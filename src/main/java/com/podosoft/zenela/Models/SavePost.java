package com.podosoft.zenela.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "save_posts")
public class SavePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saver_id")
    private Long saverId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "created_at")
    private Date createdAt;

    public SavePost() {
    }

    public SavePost(Long saverId, Long postId, Date createdAt) {
        this.saverId = saverId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaverId() {
        return saverId;
    }

    public void setSaverId(Long saverId) {
        this.saverId = saverId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
