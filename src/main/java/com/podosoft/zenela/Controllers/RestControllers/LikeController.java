package com.podosoft.zenela.Controllers.RestControllers;

import com.podosoft.zenela.Dto.LikeDto;
import com.podosoft.zenela.Models.Like;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Services.LikeService;
import com.podosoft.zenela.Services.MainService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class LikeController {
    private final LikeService likeService;
    private final MainService mainService;

    public LikeController(LikeService likeService, MainService mainService) {
        this.likeService = likeService;
        this.mainService = mainService;
    }

    @PostMapping("/like")
    public String likePost(@RequestBody LikeDto likeDto, Authentication authentication) {
        User principal = mainService.findPrincipal(authentication.getName());
        Like like = likeService.saveLike(principal.getId(), likeDto.getPostId());

        //Collection<Like> likes = likeService.findAll(likeDto.getPostId());

        return String.valueOf(likeService.findLikeSize(likeDto.getPostId()));

    }

    @PostMapping("/unlike")
    public String unlikePost(@RequestBody LikeDto likeDto, Authentication authentication) {
        User principal = mainService.findPrincipal(authentication.getName());

        likeService.deleteLike(principal.getId(), likeDto.getPostId());

        //Collection<Like> likes = likeService.findAll(likeDto.getPostId());

        return String.valueOf(likeService.findLikeSize(likeDto.getPostId()));

    }
}
