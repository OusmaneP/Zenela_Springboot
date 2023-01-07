package com.podosoft.zenela.Controllers;

import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Services.MainService;
import com.podosoft.zenela.Services.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class PostController {

    private final MainService mainService;
    private final PostService postService;

    public PostController(MainService mainService, PostService postService) {
        this.mainService = mainService;
        this.postService = postService;
    }

    @PostMapping("/create_post")
    public String createPost(@RequestParam("file") MultipartFile file, @RequestParam("comment") String comment, Authentication authentication) throws IOException {
        User principal = mainService.findPrincipal(authentication.getName());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String fileCode = postService.uploadPost(fileName, file, comment, principal.getId());
        System.out.println(fileCode);

        return "redirect:/profile";

    }
}
