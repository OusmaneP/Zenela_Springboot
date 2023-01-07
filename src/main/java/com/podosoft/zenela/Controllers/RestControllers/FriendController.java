package com.podosoft.zenela.Controllers.RestControllers;

import com.podosoft.zenela.Dto.FriendDto;
import com.podosoft.zenela.Models.Friend;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Services.FriendService;
import com.podosoft.zenela.Services.MainService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendController {

    private final MainService mainService;
    private final FriendService friendService;

    public FriendController(MainService mainService, FriendService friendService) {
        this.mainService = mainService;
        this.friendService = friendService;
    }

    @PostMapping("/send_friend_request")
    public String sendFriendRequest(@RequestBody FriendDto friendDto, Authentication authentication) {

        User principal = mainService.findPrincipal(authentication.getName());

        Friend friend = friendService.saveRequest(principal.getId(), friendDto.getUserId());

        System.out.println("friend request sent");

        return String.valueOf(friend.getId());
    }

    @PostMapping("/accept_friend_request")
    public String acceptFriendRequest(@RequestBody FriendDto friendDto, Authentication authentication) {

        User principal = mainService.findPrincipal(authentication.getName());

        int update = friendService.acceptFriendRequest(principal.getId(), friendDto.getUserId());

        System.out.println("Accepted friend request !");

        return String.valueOf(update);
    }



}
