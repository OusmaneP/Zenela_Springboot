package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Models.SavePost;
import com.podosoft.zenela.Repositories.SavePostRepository;
import com.podosoft.zenela.Services.SavePostService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class SavePostServiceImpl implements SavePostService {

    private final SavePostRepository savePostRepository;

    public SavePostServiceImpl(SavePostRepository savePostRepository) {
        this.savePostRepository = savePostRepository;
    }

    @Override
    public SavePost savePost(Long saverId, Long postId) {
        SavePost savePost = new SavePost(saverId, postId, new Date());
        return savePostRepository.save(savePost);
    }

    @Override
    public Collection<SavePost> findAll(Long saverId) {
        return savePostRepository.findAllBySaverId(saverId);
    }

    @Override
    public void deleteSavedPost(Long saverId, Long postId) {
        savePostRepository.deleteBySaverIdAndPostId(saverId, postId);
    }
}
