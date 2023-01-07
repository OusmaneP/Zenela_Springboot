package com.podosoft.zenela.Services;

import com.podosoft.zenela.Models.SavePost;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface SavePostService {

    SavePost savePost(Long saverId, Long postId);

    Collection<SavePost> findAll(Long saverId);

    void deleteSavedPost(Long saverId, Long postId);
}
