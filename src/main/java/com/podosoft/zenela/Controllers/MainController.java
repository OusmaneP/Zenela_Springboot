package com.podosoft.zenela.Controllers;

import com.podosoft.zenela.Dto.Notifications;
import com.podosoft.zenela.Models.Post;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Services.MainService;
import com.podosoft.zenela.Services.PostService;
import com.podosoft.zenela.Websocket.Services.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class MainController {

    private final MainService mainService;
    private final PostService postService;
    private final MessageService messageService;

    public MainController(MainService mainService, PostService postService, MessageService messageService) {
        this.mainService = mainService;
        this.postService = postService;
        this.messageService = messageService;
    }

    ///////       Root    Home    //////////////
    @GetMapping("/")
    public String home(Model model, Authentication authentication){
        User principal = mainService.findPrincipal(authentication.getName());

        Collection<Post> posts = postService.getNewPosts(principal.getId());

        model.addAttribute("Posts", posts);
        model.addAttribute("Principal", principal);

        // Get Friends Requests
        Collection<User> friendRequests = mainService.getFriendRequests(principal.getId());
        model.addAttribute("FriendRequests", friendRequests);

        // All FriendShips
        Collection<User> friendShips = mainService.getFriendShips(principal.getId());
        List<Long> friendShipsIds = new ArrayList<>();
        for (User user:
             friendShips) {
            friendShipsIds.add(user.getId());
        }

        // Get my Friends On Messenger
        Collection<User> myFriends = messageService.getSomeMessages(principal.getId());

        Collection<User> myFriendsCustom = new ArrayList<>();
        for (User myFriend:
             myFriends) {
            int missedMessages = messageService.getMissedMessages(principal.getId(), myFriend.getId());
            if(missedMessages > 0) {
                myFriend.setMissedMessages(missedMessages);
                myFriendsCustom.add(myFriend);
            }
            else {
                myFriend.setMissedMessages(0);
                myFriendsCustom.add(myFriend);
            }
        }
        

        model.addAttribute("MyFriends", myFriendsCustom);

        // Get Random Users
        Collection<User> randomUsers = mainService.getRandomUsers(principal.getId(), friendShipsIds);
        model.addAttribute("RandomUsers", randomUsers);

        // Get Notifications
        Notifications notifications = mainService.getNotifications(principal.getId());
        notifications.setFriendship((long) friendRequests.size());
        model.addAttribute("Notifications", notifications);

        return "index";
    }



    ///////       Profile Page    /////////////
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication){
        User principal = mainService.findPrincipal(authentication.getName());
        model.addAttribute("Principal", principal);

        // My Posts
        Collection<Post> posts = postService.getUsersPosts(principal.getId(), principal.getId());
        model.addAttribute("Posts", posts);

        // Get Friends Requests
        Collection<User> friendRequests = mainService.getFriendRequests(principal.getId());
        model.addAttribute("FriendRequests", friendRequests);

        // Get my Friends Messaging
        Collection<User> myFriends = messageService.getSomeMessages(principal.getId());

        Collection<User> myFriendsCustom = new ArrayList<>();
        for (User myFriend:
                myFriends) {
            int missedMessages = messageService.getMissedMessages(principal.getId(), myFriend.getId());
            if(missedMessages > 0) {
                myFriend.setMissedMessages(missedMessages);
                myFriendsCustom.add(myFriend);
            }
            else {
                myFriend.setMissedMessages(0);
                myFriendsCustom.add(myFriend);
            }
        }


        model.addAttribute("MyFriends", myFriendsCustom);

        // Get my General Friends
        Collection<User> myGenFriends = mainService.getMyFriends(principal.getId());
        model.addAttribute("MyGenFriends", myGenFriends);

        // Get Notifications
        Notifications notifications = mainService.getNotifications(principal.getId());
        notifications.setFriendship((long) friendRequests.size());
        model.addAttribute("Notifications", notifications);



        return "profile";
    }

    @PostMapping("/change_cover")
    public String changeCoverPhoto(@RequestParam("file") MultipartFile file, @RequestParam("comment") String comment, Authentication authentication) throws IOException {

        User principal = mainService.findPrincipal(authentication.getName());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String fileCode = mainService.uploadCoverProfile(fileName, file, comment, principal.getId(), "cover");
        System.out.println(fileCode);

        return "redirect:/profile";
    }

    @PostMapping("/change_profile")
    public String changeProfilePhoto(@RequestParam("file") MultipartFile file, @RequestParam("comment") String comment, Authentication authentication) throws IOException {

        User principal = mainService.findPrincipal(authentication.getName());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String fileCode = mainService.uploadCoverProfile(fileName, file, comment, principal.getId(), "profile");
        System.out.println(fileCode);

        return "redirect:/profile";
    }

    ///////        View Others Profile   ////////
    @GetMapping("/view_profile/{userId}")
    public String viewProfile(@PathVariable Long userId, Model model,  Authentication authentication){
        User principal = mainService.findPrincipal(authentication.getName());

        Optional<User> user = mainService.findUserById(userId);
        if(user.isPresent()){
            model.addAttribute("User", user.get());

            Collection<Post> posts = postService.getUsersPosts(userId, principal.getId());

            model.addAttribute("Posts", posts);
        }
        model.addAttribute("Principal", principal);


        // Get Friends Requests
        Collection<User> friendRequests = mainService.getFriendRequests(principal.getId());
        model.addAttribute("FriendRequests", friendRequests);

        // Get Notifications
        Notifications notifications = mainService.getNotifications(principal.getId());
        notifications.setFriendship((long) friendRequests.size());
        model.addAttribute("Notifications", notifications);

        return "view_profile";
    }

}
