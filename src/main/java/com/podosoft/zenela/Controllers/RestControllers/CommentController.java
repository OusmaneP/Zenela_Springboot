package com.podosoft.zenela.Controllers.RestControllers;

import com.podosoft.zenela.Dto.CommentDto;
import com.podosoft.zenela.Models.Comment;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Services.CommentService;
import com.podosoft.zenela.Services.MainService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final MainService mainService;

    public CommentController(CommentService commentService, MainService mainService) {
        this.commentService = commentService;
        this.mainService = mainService;
    }

    @PostMapping("/comment_post")
    public String commentPost(@RequestBody CommentDto commentDto, Authentication authentication){

        User principal = mainService.findPrincipal(authentication.getName());
        Comment comment = commentService.saveComment(principal.getId(), commentDto);

        return "Commenting went good !";
    }



}
