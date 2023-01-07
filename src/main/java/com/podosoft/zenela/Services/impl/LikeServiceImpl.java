package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Models.Like;
import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Repositories.LikeRepository;
import com.podosoft.zenela.Repositories.PostRepository;
import com.podosoft.zenela.Services.LikeService;
import com.podosoft.zenela.Services.PostNotificationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PostNotificationService postNotificationService;

    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository, PostNotificationService postNotificationService) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.postNotificationService = postNotificationService;
    }

    @Override
    public Like saveLike(Long principalId, Long postId) {
        Like like = new Like(principalId, postId, new Date());
        notifyPost(postId, principalId);
        return likeRepository.save(like);
    }

//    @Override
//    public Collection<Like> findAll(Long postId) {
//        return likeRepository.findAllByPost(postId);
//    }

    @Override
    public boolean findByLikerAndPost(Long liker, Long post){
        Optional<Like> optionalLike = likeRepository.findByLikerAndPost(liker, post);
        return optionalLike.isPresent();
    }

    @Override
    public void deleteLike(Long principalId, Long postId) {
        likeRepository.deleteByLikerAndPost(principalId, postId);
    }

    @Override
    public Long findLikeSize(Long post){
        return likeRepository.findLikeSize(post);
    }

    //////// Private Methods
    private void notifyPost(Long postId, Long principalId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){
            if (!post.get().isNotified()){
                postRepository.notifyPost(postId, true);
                postNotificationService.saveNotification(post.get().getPosterId(), principalId, postId, "like");
            }
        }
    }
}
