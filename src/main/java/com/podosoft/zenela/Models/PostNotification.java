package com.podosoft.zenela.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_notifications")
public class PostNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "notifier_id")
    private Long notifierId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "has_read")
    private boolean hasRead;

    @Transient
    private String notifierName;

    @Transient
    private String notifierProfile;



    public PostNotification() {
    }

    public PostNotification(Long receiverId, Long notifierId, Long postId, String notificationType, Date createdAt) {
        this.receiverId = receiverId;
        this.notifierId = notifierId;
        this.postId = postId;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
    }

    public PostNotification(Long receiverId, Long notifierId, Long postId, String notifierName, String notifierProfile, String notificationType, Date createdAt) {
        this.receiverId = receiverId;
        this.notifierId = notifierId;
        this.postId = postId;
        this.notifierName = notifierName;
        this.notifierProfile = notifierProfile;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getNotifierId() {
        return notifierId;
    }

    public void setNotifierId(Long notifierId) {
        this.notifierId = notifierId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }

    public String getNotifierProfile() {
        return notifierProfile;
    }

    public void setNotifierProfile(String notifierProfile) {
        this.notifierProfile = notifierProfile;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }
}
