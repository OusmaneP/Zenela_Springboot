package com.podosoft.zenela.Services;

import com.podosoft.zenela.Models.PostNotification;

import java.util.Collection;

public interface PostNotificationService {
    void saveNotification(Long receiverId, Long notifierId, Long postId, String type);

    Collection<PostNotification> getNotifications(Long principalId);

    int getUnreadNotifications(Long id);

    void readPostNotifications(Long principalId);
}
