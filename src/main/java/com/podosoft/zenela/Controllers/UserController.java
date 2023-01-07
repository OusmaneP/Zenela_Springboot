package com.podosoft.zenela.Controllers;

import com.podosoft.zenela.Dto.UserRegistrationDto;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Services.MainService;
import com.podosoft.zenela.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(){
        return "registration";
    }


    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto){
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }



}
