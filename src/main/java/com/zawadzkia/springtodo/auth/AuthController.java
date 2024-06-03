package com.zawadzkia.springtodo.auth;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/auth")
class AuthController {

    @GetMapping("/login")
    String login() {
        return "auth/login";
    }

    @GetMapping("/error")
    String error() {
        throw new NotImplementedException("Not Implemented Yet!");
    }
}
