package com.podosoft.zenela.ApiMobile.Controllers;

import com.podosoft.zenela.ApiMobile.Responses.*;
import com.podosoft.zenela.Models.*;
import com.podosoft.zenela.Repositories.RoleRepository;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Repositories.UsersRolesRepository;
import com.podosoft.zenela.Services.*;
import com.podosoft.zenela.Websocket.Services.MessageService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/mobile")
@CrossOrigin
public class ProfileController {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final UsersRolesRepository usersRolesRepository;

    final MainService mainService;
    final PostService postService;
    final MessageService messageService;
    final LikeService likeService;
    final CommentService commentService;
    final FriendService friendService;
    final SavePostService savePostService;
    final PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, RoleRepository roleRepository, UsersRolesRepository usersRolesRepository, MainService mainService, PostService postService, MessageService messageService, LikeService likeService, CommentService commentService, FriendService friendService, SavePostService savePostService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.mainService = mainService;
        this.postService = postService;
        this.messageService = messageService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.friendService = friendService;
        this.savePostService = savePostService;
        this.passwordEncoder = passwordEncoder;
    }

    // my Profile
    @GetMapping("/profile")
    public ProfileResponse profile(@RequestParam("email") String email) {
        User principal = mainService.findPrincipal(email);

        // Get my General Friends
        Collection<User> myGenFriends = mainService.getMyFriends(principal.getId());

        // Get Friends Requests
        Collection<User> friendRequests = mainService.getFriendRequests(principal.getId());

        // Get Friends I Invited
        Collection<User> invitedFriends = mainService.getInvitedFriends(principal.getId());

        //My Posts
        Collection<Post> posts = postService.getUsersImages(principal.getId(), principal.getId());

        ProfileResponse profileResponse = new ProfileResponse("body ok", "Request Ok");
        profileResponse.setPrincipal(principal);
        profileResponse.setMyFriends(myGenFriends);
        profileResponse.setFriendRequests(friendRequests);
        profileResponse.setInvitedFriends(invitedFriends);
        profileResponse.setPosts(posts);

        return profileResponse;
    }

    // view Profile
    @GetMapping("/view_profile")
    public ProfileResponse profile(@RequestParam("userId") Long userId, @RequestParam("principalId") Long principalId) throws InterruptedException {
        Optional<User> userOptional = mainService.findUserById(userId);
        User user ;
        if (userOptional.isPresent()){
            user = userOptional.get();

            // Get my General Friends
            Collection<User> myGenFriends = mainService.getMyFriends(user.getId());

            // Get Friends Requests
            Collection<User> friendRequests = mainService.getFriendRequests(user.getId());

            // Get Friends I Invited
            Collection<User> invitedFriends = mainService.getInvitedFriends(user.getId());

            //My Posts
            Collection<Post> posts = postService.getUsersImages(user.getId(), user.getId());


            ProfileResponse profileResponse = new ProfileResponse("body ok", "Request Ok");
            profileResponse.setPrincipal(user);
            profileResponse.setMyFriends(myGenFriends);
            profileResponse.setFriendRequests(friendRequests);
            profileResponse.setInvitedFriends(invitedFriends);
            profileResponse.setPosts(posts);

            // check if He is friends with principal
            for (User user1: myGenFriends){
                if (user1.getId().equals(principalId)){
                    profileResponse.setFriends(true);
                    return profileResponse;
                }
            }

            // check if He is friends with principal
            for (User user1: invitedFriends){
                if (user1.getId().equals(principalId)){
                    profileResponse.setFriends(true);
                    return profileResponse;
                }
            }

            // check if He is friends with principal
            for (User user1: friendRequests){
                if (user1.getId().equals(principalId)){
                    profileResponse.setFriends(true);
                    return profileResponse;
                }
            }

            return profileResponse;
        }

        return new ProfileResponse("body empty", "Request Ok");
    }

    // send Friend Request
    @PostMapping("/send_friend_request")
    public LoginResponse sendFriendRequest(@RequestParam("userId") Long userId, @RequestParam("principalId") Long principalId) {

        Friend friend = friendService.saveRequest(principalId, userId);

        System.out.println("friend request sent " + friend.getId());

        return new LoginResponse("Ok", "Request Ok");
    }

    // Accept Friend Request
    @PostMapping("/accept_friend_request")
    public LoginResponse acceptFriendRequest(@RequestParam("userId") Long userId, @RequestParam("principalId") Long principalId) {

        int update = friendService.acceptFriendRequest(principalId, userId);

        return new LoginResponse("Ok", "Request Ok");
    }

    // Accept Friend Request
    @PostMapping("/cancel_friend")
    public LoginResponse cancelFriend(@RequestParam("userId") Long userId, @RequestParam("principalId") Long principalId) {

        int update = friendService.cancelFriend(principalId, userId);

        return new LoginResponse("Ok", "Request Ok");
    }

    @GetMapping("/search")
    public SearchResponse searchPeople(@RequestParam("query") String query, @RequestParam("principalId") Long principalId){
        Collection<User> users = userRepository.searchName(query);
        SearchResponse searchResponse = new SearchResponse("body ok", "Request Ok", users);
        return searchResponse;
    }

    @GetMapping("/search_random")
    public SearchResponse searchRandomPeople(@RequestParam("principalId") Long principalId){
        Collection<User> users = userRepository.findAllRandom(List.of(principalId));
        SearchResponse searchResponse = new SearchResponse("body ok", "Request Ok", users);
        return searchResponse;
    }

    @PostMapping("/create_post")
    public LoginResponse createPost(@RequestParam("file") MultipartFile file, @RequestParam("comment") String comment, @RequestParam("principalId") String principalId, @RequestParam("type") String type) throws IOException {


        Long principalIdLong = Long.valueOf(principalId);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileCode = "";
        // type post Image
        switch (type) {

            case "cover":
                fileCode = mainService.uploadCoverProfile(fileName, file, comment, principalIdLong, "cover");
                break;
            case "profile":
                fileCode = mainService.uploadCoverProfile(fileName, file, comment, principalIdLong, "profile");
                break;
            default:
                fileCode = postService.uploadPost(fileName, file, comment, principalIdLong);
                break;
        }

        System.out.println(fileCode);

        return new LoginResponse("ok", "Request ok");

    }

    //////   Find All Saved Post
    @GetMapping("/find_saved_posts")
    public RandomPostsResponse findSavePosts(@RequestParam("principalId") Long principalId){
        Collection<SavePost> savePosts = savePostService.findAll(principalId);

        ArrayList<Long> postIds = new ArrayList<>();

        for (SavePost savedPost: savePosts) {
            postIds.add(savedPost.getPostId());
        }

        Collection<Post> customPosts = new ArrayList<>();

        Collection<Post> posts = postService.getSavedPosts(postIds, principalId);
        for (Post post: posts) {
            post.setSavingPossibility(0);
            customPosts.add(post);
        }

        return new RandomPostsResponse("saved ok", "Request Ok", customPosts);
    }



    // Edit Profile Infos
    @PostMapping("/edit_profile")
    public LoginResponse editProfile(@RequestParam("principalId") Long principalId,
                                     @RequestParam("first_name") String firstName,
                                     @RequestParam("last_name") String lastName,
                                     @RequestParam("email") String email,
                                     @RequestParam("bio") String bio) {

        if(firstName.isEmpty() || email.isEmpty()){
            return new LoginResponse("Please Complete all Fields", "Bad Request");
        }

        Optional<User> principalOptional = mainService.findUserById(principalId);
        if (principalOptional.isPresent()){
            User principal = principalOptional.get();
            principal.setFirstName(firstName);
            principal.setLastName(lastName);
            principal.setEmail(email);
            principal.setBio(bio);

            userRepository.save(principal);

            LoginResponse loginResponse = new LoginResponse("Operation completed successfully", "Request Ok");
            loginResponse.setPrincipal(principal);

            return loginResponse;
        }

        return new LoginResponse("Operation failed", "Bad Request");
    }

    // Edit Password
    @PostMapping("/edit_password")
    public LoginResponse editPassword(@RequestParam("principalId") Long principalId,
                                     @RequestParam("old_password") String oldPassword,
                                     @RequestParam("new_password") String newPassword) {

        Optional<User> principalOptional = mainService.findUserById(principalId);

        if (principalOptional.isPresent()){

            User principal = principalOptional.get();

            // if account exist and password doesn't match
            if (!(BCrypt.checkpw(oldPassword, principal.getPassword())))
                return new LoginResponse("Old password wrong", "Bad Request");
            else{
                if (newPassword.length() > 0){
                    principal.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(principal);
                    LoginResponse loginResponse = new LoginResponse("Password changed successfully", "Request Ok");
                    loginResponse.setPrincipal(principal);
                    return loginResponse;
                }
            }

        }

        return new LoginResponse("Unsuccessful operation", "Bad Request");
    }

    //  Delete my Post
    @PostMapping("/delete_post")
    public LoginResponse deletePost(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId) {
        Optional<Post> postOptional = postService.findPostById(postId);

        if (postOptional.isPresent()){
            Post post = postOptional.get();
            if (post.getPosterId().equals(principalId)) {
                if (postService.deletePost(post.getId(), post.getFileName())) {
                    return new LoginResponse("Deleted Successfully", "Request Ok");
                }else
                    return new LoginResponse("Couldn't delete", "Bad Request");
            }else
                return new LoginResponse("Not the owner", "Bad Request");
        }else
            return new LoginResponse("Post couldn't be found", "Bad Request");

    }

    @PostMapping("/update_post_comment")
    public LoginResponse updatePostComment(@RequestParam("principalId") Long principalId, @RequestParam("postId") Long postId, @RequestParam("comment") String comment){
        postService.updatePostComment(postId, comment);

        return new LoginResponse("Updated Successfully", "Request Ok");
    }

    @PostMapping("/create_video")
    public LoginResponse createVideo(@RequestParam("file") MultipartFile file, @RequestParam("thumb") MultipartFile thumb, @RequestParam("comment") String comment, @RequestParam("principalId") Long principalId) throws IOException {
        System.out.println("went well " + file.getSize() + " " + file.getContentType());
        System.out.println("other one " + thumb.getSize() + " " + thumb.getContentType());
        System.out.println("other one " + thumb.getSize() + " " + StringUtils.getFilenameExtension(thumb.getOriginalFilename()));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String thumbName = StringUtils.cleanPath(Objects.requireNonNull(thumb.getOriginalFilename()));
        String fileCode = "";
        String thumbCode = "";

        fileCode = postService.uploadVideo(fileName, thumbName,  file, thumb, comment, principalId);


        System.out.println(fileCode);
        return new LoginResponse("ok", "Request ok");
    }

    //////   Find All Saved Post
    @GetMapping("/get_my_videos")
    public RandomVideosResponse findMyVideos(@RequestParam("principalId") Long principalId){
        Collection<Post> videos = postService.findMyVideos(principalId);

        return new RandomVideosResponse("saved ok", "Request Ok", videos);
    }

}
