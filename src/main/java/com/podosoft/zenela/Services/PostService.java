package com.podosoft.zenela.Services;

import com.podosoft.zenela.Models.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public interface PostService {
    String uploadPost(String fileName, MultipartFile file, String comment, Long posterId) throws IOException;

    boolean deletePost(Long postId, String url);

    Optional<Post> findPostById(Long postId);

    void updatePostComment(Long postId, String comment);

    Collection<Post> getNewPosts(Long principalId);

    // Get NewPosts
    Collection<Post> getNewVideos(Long principalId);

    Collection<Post> getUsersPosts(Long userId, Long principalId);

    Collection<Post> getUsersImages(Long userId, Long principalId);

    Collection<Post> getSavedPosts(ArrayList<Long> postIds, Long principalId);

    Post viewPost(Long principalId, Long postId);

    // Upload Post
    String uploadVideo(String fileName, String thumbName, MultipartFile file, MultipartFile thumb, String comment, Long posterId) throws IOException;

    Collection<Post> findMyVideos(Long principalId);
}
