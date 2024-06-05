package com.zawadzkia.springtodo.auth;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawadzkia.springtodo.task.TaskService;
import com.zawadzkia.springtodo.user.UserDTO;
import com.zawadzkia.springtodo.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    String register() {
        return "auth/register";
    }
    @PostMapping("/register")
    String register(@ModelAttribute UserDTO userDTO) {
        userService.create(userDTO);
        return "auth/login";
    }
    @GetMapping("/error")
    String error() {
        throw new NotImplementedException("Not Implemented Yet!");
    }
}
