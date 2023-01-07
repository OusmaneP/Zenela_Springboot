package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Models.*;
import com.podosoft.zenela.Repositories.PostRepository;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Services.CommentService;
import com.podosoft.zenela.Services.FileService;
import com.podosoft.zenela.Services.LikeService;
import com.podosoft.zenela.Services.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeService likeService;
    private final CommentService commentService;
    private final FileService fileService;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LikeService likeService, CommentService commentService, FileService fileService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeService = likeService;
        this.commentService = commentService;
        this.fileService = fileService;
    }

    // Upload Post
    @Override
    public String uploadPost(String fileName, MultipartFile file, String comment, Long posterId) throws IOException {

        String type = file.getContentType();

        String publicUrl = fileService.uploadFile(file);
        Post post = new Post(posterId, comment, type, publicUrl, new Date());

        Post savedPost = postRepository.save(post);

        return publicUrl;
    }

    // Delete Post
    @Override
    public boolean deletePost(Long postId, String url){
        boolean deleted = fileService.deleteFile(url);

        if (deleted){
            postRepository.deleteById(postId);
            System.out.println("All went well");
            return true;
        }
        return false;
    }

    // Find PostByID
    @Override
    public Optional<Post> findPostById(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        return post;
    }

    @Override
    public void updatePostComment(Long postId, String comment){
        postRepository.updatePostComment(postId, comment);
    }

    // Get NewPosts
    @Override
    public Collection<Post> getNewPosts(Long principalId) {
        Collection<Post> posts = postRepository.findRandomTypeLimit("image");
        Collection<Post> customPosts = new ArrayList<>();

        for (Post post:
             posts) {
            // get the post owner
            Optional<User> owner = userRepository.findById(post.getPosterId());


            if(owner.isPresent()){
                post.setPosterName(owner.get().getFirstName() + "  " + owner.get().getLastName());
                post.setPosterProfile(owner.get().getProfile());

                post.setLikingPossibility(new LikingPossibility());
                post.getLikingPossibility().setPossible(1);

                // All likes
                //Collection<Like> likes = likeService.findAll(post.getId());

                post.getLikingPossibility().setTotalLikes(likeService.findLikeSize(post.getId()));

                // liking possibility
                if (likeService.findByLikerAndPost(principalId, post.getId()))
                    post.getLikingPossibility().setPossible(0);

//                for (Like like:
//                     likes) {
//                    if(Objects.equals(like.getLiker(), principalId)){
//                        post.getLikingPossibility().setPossible(0);
//                        break;
//                    }
//                }

                // get post's Comments
                Collection<Comment> commentsRecent = commentService.findAllRecentPost(post.getId());

                //Collection<Comment> commentsAll = commentService.findAllByPost(post.getId());
                post.setTotalComments(commentService.findCommentSize(post.getId()));

                for (Comment comment:
                     commentsRecent) {
                    Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                    if (commentOwner.isPresent()){
                        comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                        comment.setCommenterProfile(commentOwner.get().getProfile());
                        post.getCommentsList().add(comment);
                    }
                }

                customPosts.add(post);
            }
        }

        return customPosts;
    }

    // Get NewPosts
    @Override
    public Collection<Post> getNewVideos(Long principalId) {
        Collection<Post> posts = postRepository.findRandomTypeLimit("video");
        Collection<Post> customPosts = new ArrayList<>();

        for (Post post:
                posts) {
            // get the post owner
            Optional<User> owner = userRepository.findById(post.getPosterId());


            if(owner.isPresent()){
                post.setPosterName(owner.get().getFirstName() + "  " + owner.get().getLastName());
                post.setPosterProfile(owner.get().getProfile());

                post.setLikingPossibility(new LikingPossibility());
                post.getLikingPossibility().setPossible(1);

                // All likes
                //Collection<Like> likes = likeService.findAll(post.getId());

                post.getLikingPossibility().setTotalLikes(likeService.findLikeSize(post.getId()));

                // liking possibility
                if (likeService.findByLikerAndPost(principalId, post.getId()))
                    post.getLikingPossibility().setPossible(0);

//                for (Like like:
//                     likes) {
//                    if(Objects.equals(like.getLiker(), principalId)){
//                        post.getLikingPossibility().setPossible(0);
//                        break;
//                    }
//                }

                // get post's Comments
                Collection<Comment> commentsRecent = commentService.findAllRecentPost(post.getId());

                //Collection<Comment> commentsAll = commentService.findAllByPost(post.getId());
                post.setTotalComments(commentService.findCommentSize(post.getId()));

                for (Comment comment:
                        commentsRecent) {
                    Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                    if (commentOwner.isPresent()){
                        comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                        comment.setCommenterProfile(commentOwner.get().getProfile());
                        post.getCommentsList().add(comment);
                    }
                }

                customPosts.add(post);
            }
        }

        return customPosts;
    }

    @Override
    public Collection<Post> getUsersPosts(Long userId, Long principalId) {

        Collection<Post> posts = postRepository.findAllByPosterIdOrderByIdDesc(userId);
        Collection<Post> customPosts = new ArrayList<>();

        for (Post post:
                posts) {
            // get the post owner
            Optional<User> owner = userRepository.findById(post.getPosterId());


            if(owner.isPresent()){
                post.setPosterName(owner.get().getFirstName() + "  " + owner.get().getLastName());
                post.setPosterProfile(owner.get().getProfile());

                post.setLikingPossibility(new LikingPossibility());
                post.getLikingPossibility().setPossible(1);



                post.getLikingPossibility().setTotalLikes(likeService.findLikeSize(post.getId()));

                if (likeService.findByLikerAndPost(principalId, post.getId()))
                    post.getLikingPossibility().setPossible(0);

                // get post's Comments
                Collection<Comment> commentsRecent = commentService.findAllRecentPost(post.getId());

                post.setTotalComments(commentService.findCommentSize(post.getId()));

                for (Comment comment:
                        commentsRecent) {
                    Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                    if (commentOwner.isPresent()){
                        comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                        comment.setCommenterProfile(commentOwner.get().getProfile());
                        post.getCommentsList().add(comment);
                    }
                }

                customPosts.add(post);
            }
        }

        return customPosts;

    }

    @Override
    public Collection<Post> getUsersImages(Long userId, Long principalId) {

        Collection<Post> posts = postRepository.findAllByPosterIdOrderByIdDesc(userId);
        Collection<Post> customPosts = new ArrayList<>();

        for (Post post :
                posts) {
            if (post.getType().contains("image")) {
                // get the post owner
                Optional<User> owner = userRepository.findById(post.getPosterId());


                if (owner.isPresent()) {
                    post.setPosterName(owner.get().getFirstName() + "  " + owner.get().getLastName());
                    post.setPosterProfile(owner.get().getProfile());

                    post.setLikingPossibility(new LikingPossibility());
                    post.getLikingPossibility().setPossible(1);


                    post.getLikingPossibility().setTotalLikes(likeService.findLikeSize(post.getId()));

                    if (likeService.findByLikerAndPost(principalId, post.getId()))
                        post.getLikingPossibility().setPossible(0);

                    // get post's Comments
                    Collection<Comment> commentsRecent = commentService.findAllRecentPost(post.getId());

                    post.setTotalComments(commentService.findCommentSize(post.getId()));

                    for (Comment comment :
                            commentsRecent) {
                        Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                        if (commentOwner.isPresent()) {
                            comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                            comment.setCommenterProfile(commentOwner.get().getProfile());
                            post.getCommentsList().add(comment);
                        }
                    }

                    customPosts.add(post);
                }
            }
        }

        return customPosts;

    }

    @Override
    public Collection<Post> getSavedPosts(ArrayList<Long> postIds, Long principalId) {

        Collection<Post> posts = postRepository.findAllById(postIds);
        Collection<Post> customPosts = new ArrayList<>();

        for (Post post :
                posts) {

            // get the post owner
            Optional<User> owner = userRepository.findById(post.getPosterId());


            if (owner.isPresent()) {
                post.setPosterName(owner.get().getFirstName() + "  " + owner.get().getLastName());
                post.setPosterProfile(owner.get().getProfile());

                post.setLikingPossibility(new LikingPossibility());
                post.getLikingPossibility().setPossible(1);


                post.getLikingPossibility().setTotalLikes(likeService.findLikeSize(post.getId()));

                if (likeService.findByLikerAndPost(principalId, post.getId()))
                    post.getLikingPossibility().setPossible(0);

                // get post's Comments
                Collection<Comment> commentsRecent = commentService.findAllRecentPost(post.getId());

                post.setTotalComments(commentService.findCommentSize(post.getId()));

                for (Comment comment :
                        commentsRecent) {
                    Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                    if (commentOwner.isPresent()) {
                        comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                        comment.setCommenterProfile(commentOwner.get().getProfile());
                        post.getCommentsList().add(comment);
                    }
                }

                customPosts.add(post);
            }

        }

        return customPosts;

    }

    @Override
    public Post viewPost(Long principalId, Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()) {
            Post customPost = post.get();

            // get the post owner
            Optional<User> owner = userRepository.findById(customPost.getPosterId());


            if (owner.isPresent()) {
                customPost.setPosterName(owner.get().getFirstName() + "  " + owner.get().getLastName());
                customPost.setPosterProfile(owner.get().getProfile());

                customPost.setLikingPossibility(new LikingPossibility());
                customPost.getLikingPossibility().setPossible(1);

                customPost.getLikingPossibility().setTotalLikes(likeService.findLikeSize(customPost.getId()));

                if (likeService.findByLikerAndPost(principalId, customPost.getId()))
                    customPost.getLikingPossibility().setPossible(0);

                // get post's Comments
                Collection<Comment> commentsAll = commentService.findAllByPost(customPost.getId());
                customPost.setTotalComments(commentService.findCommentSize(customPost.getId()));

                for (Comment comment :
                        commentsAll) {
                    Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                    if (commentOwner.isPresent()) {
                        comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                        comment.setCommenterProfile(commentOwner.get().getProfile());
                        customPost.getCommentsList().add(comment);
                    }
                }

                return customPost;

            }
        }
        return null;
    }

    // Upload Post
    @Override
    public String uploadVideo(String fileName, String thumbName, MultipartFile file, MultipartFile thumb, String comment, Long posterId) throws IOException {

        String fileType = file.getContentType();

        String fileUrl = fileService.uploadFile(file);
        String thumbUrl = fileService.uploadThumb(thumb);
        Post post = new Post(posterId, comment, fileType, fileUrl, new Date());
        post.setThumbnail(thumbUrl);

        postRepository.save(post);

        return fileUrl + " \n" + thumbUrl;
    }

    @Override
    public Collection<Post> findMyVideos(Long principalId) {

        Collection<Post> posts;
        Collection<Post> customPosts = new ArrayList<>();

        // get the post owner
        Optional<User> owner = userRepository.findById(principalId);
        if (owner.isPresent()) {
            User principal = owner.get();

            posts = postRepository.findMyPostVideoTypeLimit("video", principalId);


            for (Post post :
                    posts) {

                post.setPosterName(principal.getFirstName() + "  " + principal.getLastName());
                post.setPosterProfile(principal.getProfile());

                post.setLikingPossibility(new LikingPossibility());
                post.getLikingPossibility().setPossible(1);

                // All likes
                //Collection<Like> likes = likeService.findAll(post.getId());

                post.getLikingPossibility().setTotalLikes(likeService.findLikeSize(post.getId()));

                // liking possibility
                if (likeService.findByLikerAndPost(principalId, post.getId()))
                    post.getLikingPossibility().setPossible(0);

//                for (Like like:
//                     likes) {
//                    if(Objects.equals(like.getLiker(), principalId)){
//                        post.getLikingPossibility().setPossible(0);
//                        break;
//                    }
//                }

                // get post's Comments
                Collection<Comment> commentsRecent = commentService.findAllRecentPost(post.getId());

                //Collection<Comment> commentsAll = commentService.findAllByPost(post.getId());
                post.setTotalComments(commentService.findCommentSize(post.getId()));

                for (Comment comment :
                        commentsRecent) {
                    Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

                    if (commentOwner.isPresent()) {
                        comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                        comment.setCommenterProfile(commentOwner.get().getProfile());
                        post.getCommentsList().add(comment);
                    }
                }

                customPosts.add(post);
            }
        }


        return customPosts;

    }


}













