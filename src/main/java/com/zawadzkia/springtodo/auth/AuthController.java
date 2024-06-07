package com.zawadzkia.springtodo.auth;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zawadzkia.springtodo.exception.UsernameAlreadyTakenException;
import com.zawadzkia.springtodo.task.TaskService;
import com.zawadzkia.springtodo.user.UserDTO;
import com.zawadzkia.springtodo.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.create(userDTO);
        } catch (UsernameAlreadyTakenException e) {
            bindingResult.rejectValue("username", "error.username", e.getMessage());
            return "auth/register";
        }

        return "auth/login";
    }
    @GetMapping("/error")
    String error() {
        throw new NotImplementedException("Not Implemented Yet!");
    }
}
