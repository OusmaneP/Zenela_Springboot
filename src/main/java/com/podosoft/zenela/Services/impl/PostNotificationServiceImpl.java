package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.PostNotification;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Repositories.PostNotificationRepository;
import com.podosoft.zenela.Repositories.PostRepository;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Services.PostNotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class PostNotificationServiceImpl implements PostNotificationService {
    final PostNotificationRepository postNotificationRepository;
    final UserRepository userRepository;
    final PostRepository postRepository;

    public PostNotificationServiceImpl(PostNotificationRepository postNotificationRepository, UserRepository userRepository, PostRepository postRepository) {
        this.postNotificationRepository = postNotificationRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void saveNotification(Long receiverId, Long notifierId, Long postId, String type) {
        PostNotification postNotification = new PostNotification(receiverId, notifierId, postId, type, new Date());
        postNotification.setHasRead(false);
        postNotificationRepository.save(postNotification);
    }

    @Override
    public Collection<PostNotification> getNotifications(Long principalId) {
        Collection<PostNotification> notifications= postNotificationRepository.findAllByReceiverIdOrderByIdDesc(principalId);
        Collection<PostNotification> customNotifications = new ArrayList<>();

        for (PostNotification notification: notifications) {
            Optional<User> user = userRepository.findById(notification.getNotifierId());
            Optional<Post> post = postRepository.findById(notification.getPostId());
            if (user.isPresent() && post.isPresent()){
                notification.setNotifierName(user.get().getFirstName() + " " + user.get().getLastName());
                notification.setNotifierProfile(user.get().getProfile());
                customNotifications.add(notification);
            }
        }


        return customNotifications;
    }

    @Override
    public int getUnreadNotifications(Long principalId) {

        return postNotificationRepository.findAllByReceiverIdAndHasReadOrderByIdDesc(principalId, false);
    }

    @Override
    public void readPostNotifications(Long principalId) {
        postNotificationRepository.readPostNotifications(principalId, true);
        postRepository.unNotifyPost(principalId, false);
    }
}
