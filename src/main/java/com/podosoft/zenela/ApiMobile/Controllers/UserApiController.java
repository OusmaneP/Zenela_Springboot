package com.podosoft.zenela.ApiMobile.Controllers;

import com.podosoft.zenela.Dto.CommentDto;
import com.podosoft.zenela.Models.*;
import com.podosoft.zenela.Repositories.RoleRepository;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Repositories.UsersRolesRepository;
import com.podosoft.zenela.Services.*;
import com.podosoft.zenela.Websocket.Services.MessageService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.podosoft.zenela.ApiMobile.Responses.*;

import java.util.*;


@RestController
@RequestMapping("/mobile")
@CrossOrigin
public class UserApiController {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final UsersRolesRepository usersRolesRepository;

    final MainService mainService;
    final PostService postService;
    final MessageService messageService;
    final LikeService likeService;
    final CommentService commentService;
    final SavePostService savePostService;
    final ReportService reportService;
    private final PostNotificationService postNotificationService;

    private final PasswordEncoder passwordEncoder;

    public UserApiController(UserRepository userRepository, RoleRepository roleRepository, UsersRolesRepository usersRolesRepository, MainService mainService, PostService postService, MessageService messageService, LikeService likeService, CommentService commentService, SavePostService savePostService, ReportService reportService, PostNotificationService postNotificationService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.mainService = mainService;
        this.postService = postService;
        this.messageService = messageService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.savePostService = savePostService;
        this.reportService = reportService;
        this.postNotificationService = postNotificationService;
        this.passwordEncoder = passwordEncoder;
    }


    //  Login
    @GetMapping(path = "/login", produces = "application/json")
    public LoginResponse login(@RequestParam("email") String email,
                               @RequestParam("password") String password)
    {
        // if email and password empty
        if(email.isEmpty() || password.isEmpty()){
            return new LoginResponse("Please Complete all Fields", "Bad Request");
        }

        User principal = mainService.findPrincipal(email);

        //if account doesn't exist
        if (principal== null){
            return new LoginResponse("Account with this email doesn't exist", "Bad Request");
        }

        // if account exist and password doesn't match
        if (!(BCrypt.checkpw(password, principal.getPassword())))
            return new LoginResponse("Email or password wrong", "Bad Request");
        else{
            LoginResponse loginResponse = new LoginResponse(principal.getFirstName(), "Request Ok");
            loginResponse.setPrincipal(principal);

            int unreadPostNotifications = postNotificationService.getUnreadNotifications(principal.getId());
            loginResponse.setPostNotifications(unreadPostNotifications);

            // Get Friends Requests
            Collection<User> friendRequests = mainService.getFriendRequests(principal.getId());
            loginResponse.setFriendRequestsNotification(friendRequests.size());

            return loginResponse;
        }
    }

    //  Register
    @PostMapping(path = "/register")
    public LoginResponse save(@RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        if(firstName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new LoginResponse("Please Complete all Fields", "Bad Request");
        }


        User principal = mainService.findPrincipal(email);

        //if account exist
        if (principal!= null){
            return new LoginResponse("Account with this email exists", "Bad Request");
        }

        User user = new User(firstName, lastName,
                email, passwordEncoder.encode(password),true, true, null, "posts/profile3.png", "posts/cover1.jpg", new Date());

        User registeredUser = userRepository.save(user);

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Role roleUser = roleRepository.findByName("ROLE_USER");
        Role roleAgent = roleRepository.findByName("ROLE_AGENT");

        List<UsersRoles> usersRolesCollection = new ArrayList<>();

        UsersRoles usersRole = new UsersRoles();
        usersRole.setUserId(registeredUser.getId());

        if (registeredUser.getFirstName().equals("admin")) {
            usersRole.setRoleId(roleAdmin.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));

            usersRole.setRoleId(roleAgent.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));

            usersRole.setRoleId(roleUser.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));
        }
        else if (registeredUser.getFirstName().equals("agent")){
            usersRole.setRoleId(roleAgent.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));

            usersRole.setRoleId(roleUser.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));
        }
        else{
            usersRole.setRoleId(roleUser.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));
        }

        usersRolesRepository.saveAll(usersRolesCollection);

        return new LoginResponse("Account created successfully", "Request Ok");

    }


    //   Random Posts
    @GetMapping(path = "/posts")
    public RandomPostsResponse getRandomPosts(@RequestParam("email") String email) {

        User principal = mainService.findPrincipal(email);

        //get new Posts
        Collection<Post> posts = postService.getNewPosts(principal.getId());

        // posts Images
//        Collection<Post> postsImages = new ArrayList<>();
//        for (Post post: posts) {
//            if (post.getType().equals("image"))
//                postsImages.add(post);
//        }

        RandomPostsResponse randomPostsResponse = new RandomPostsResponse(principal.getFirstName(), "Request Ok");
        randomPostsResponse.setPrincipal(principal);
        randomPostsResponse.setPosts(posts);


        return randomPostsResponse;
    }

    //  Like a Post
    @PostMapping("/like")
    public LoginResponse likePost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId) {
        Like like = likeService.saveLike(principalId, postId);

        //Collection<Like> likes = likeService.findAll(postId);

        return new LoginResponse(String.valueOf(likeService.findLikeSize(postId)), "Request Ok");

    }

    //  Unlike a Post
    @PostMapping("/unlike")
    public LoginResponse unlikePost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId) {

        likeService.deleteLike(principalId, postId);

        //Collection<Like> likes = likeService.findAll(postId);

        return new LoginResponse(String.valueOf(likeService.findLikeSize(postId)), "Request Ok");

    }

    @PostMapping("/comment_post")
    public CommentResponse commentPost(@RequestBody() CommentDto commentDto, Long principalId) {

        Comment com = commentService.saveComment(principalId, commentDto);

        Collection<Comment> commentsRecent = commentService.findAllRecentPost(commentDto.getPostId());
        List<Comment> commentList = new ArrayList<>();

        for (Comment comment:
                commentsRecent) {
            Optional<User> commentOwner = userRepository.findById(comment.getCommenter());

            if (commentOwner.isPresent()){
                comment.setCommenterName(commentOwner.get().getFirstName() + " " + commentOwner.get().getLastName());
                comment.setCommenterProfile(commentOwner.get().getProfile());
            }
            commentList.add(comment);
        }

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentList(commentList);

        return commentResponse;
    }

    //   Random Posts
    @GetMapping(path = "/videos")
    public RandomVideosResponse getRandomVideos(@RequestParam("email") String email) {

        User principal = mainService.findPrincipal(email);

        //get new Videos
        Collection<Post> videos = postService.getNewVideos(principal.getId());

        RandomVideosResponse randomVideosResponse = new RandomVideosResponse(principal.getFirstName(), "Request Ok");
        randomVideosResponse.setPrincipal(principal);
        randomVideosResponse.setPosts(videos);

        return randomVideosResponse;
    }


    //////   Save Post
    @PostMapping("/save_post")
    public LoginResponse savePost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId){
        savePostService.savePost(principalId, postId);
        return new LoginResponse("saved ok", "Request Ok");
    }

    //////   UnSave Post
    @PostMapping("/un_save_post")
    public LoginResponse unSavePost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId){
        savePostService.deleteSavedPost(principalId, postId);

        return new LoginResponse("unSaved ok", "Request Ok");
    }

    ////*****   UnSave Post  ******
    @PostMapping("/report_post")
    public LoginResponse reportPost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId, @RequestParam("level") int level, @RequestParam("reason") String reason, @RequestParam("comment") String comment){

        Report report = new Report(postId, principalId, level, reason, comment, new Date());

        reportService.saveReport(report);

        return new LoginResponse("saved ok", "Request Ok");
    }

    ////*****  Post  Notifications******
    @GetMapping("/post_notifications")
    public PostNotificationResponse getPostNotifications(@RequestParam("principalId") Long principalId) {

        Collection<PostNotification> postNotifications = postNotificationService.getNotifications(principalId);

        PostNotificationResponse response = new PostNotificationResponse(
                "body ok", "Request Ok",
                postNotifications
        );


        return response;
    }

    ////*****  Post  Notifications******
    @GetMapping("/view_post")
    public ViewPostResponse viewPost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId){
        Post post = postService.viewPost(principalId, postId);
        Optional<User> optionalPrincipal = userRepository.findById(principalId);

        ViewPostResponse response = new ViewPostResponse();
        response.setPost(post);

        if (optionalPrincipal.isPresent()){
            User principal = optionalPrincipal.get();
            response.setPrincipal(principal);
        }

        return response;
    }

    //// **** Mark Read Post Notifications
    @PostMapping("/read_post_notifications")
    public LoginResponse readPostNotifications(@RequestParam("principalId") Long principalId){
        postNotificationService.readPostNotifications(principalId);

        return new LoginResponse("read ok", "Request Ok");
    }


}
