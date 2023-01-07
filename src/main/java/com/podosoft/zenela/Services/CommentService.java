package com.podosoft.zenela.Services;

import com.podosoft.zenela.Dto.CommentDto;
import com.podosoft.zenela.Models.Comment;

import java.util.Collection;

public interface CommentService {
    Comment saveComment(Long principalId, CommentDto commentDto);

    Collection<Comment> findAllByPost(Long postId);

    Collection<Comment> findAllRecentPost(Long id);

    long findCommentSize(Long postId);
}
