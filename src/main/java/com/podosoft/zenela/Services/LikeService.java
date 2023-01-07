package com.podosoft.zenela.Services;

import com.podosoft.zenela.Models.Like;

import java.util.Collection;

public interface LikeService {
    Like saveLike(Long id, Long postId);

    //Collection<Like> findAll(Long postId);

    boolean findByLikerAndPost(Long liker, Long post);

    void deleteLike(Long id, Long postId);

    Long findLikeSize(Long post);
}
