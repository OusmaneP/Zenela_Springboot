package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Dto.CommentDto;
import com.podosoft.zenela.Models.Comment;
import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Repositories.CommentRepository;
import com.podosoft.zenela.Repositories.PostRepository;
import com.podosoft.zenela.Services.CommentService;
import com.podosoft.zenela.Services.PostNotificationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostNotificationService postNotificationService;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, PostNotificationService postNotificationService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postNotificationService = postNotificationService;
    }


    @Override
    public Comment saveComment(Long principalId, CommentDto commentDto) {
        Comment comment = new Comment(principalId, commentDto.getPostId(), commentDto.getCommentText(), new Date());
        notifyPost(commentDto.getPostId(), principalId);
        return commentRepository.save(comment);
    }

    @Override
    public Collection<Comment> findAllByPost(Long postId) {
        return commentRepository.findAllByPostOrderByIdDesc(postId);
    }

    @Override
    public Collection<Comment> findAllRecentPost(Long postId) {
        return commentRepository.findAllRecentPost(postId);
    }

    @Override
    public long findCommentSize(Long postId){
        return commentRepository.findCommentSize(postId);
    }


    //////// Private methods
    private void notifyPost(Long postId, Long principalId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){
            if (!post.get().isNotified()){
                postRepository.notifyPost(postId, true);
                postNotificationService.saveNotification(post.get().getPosterId(), principalId, postId, "comment");
            }
        }
    }

}
