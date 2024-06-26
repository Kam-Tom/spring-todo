package com.zawadzkia.springtodo.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index"})
@Slf4j
public class IndexController {

    @GetMapping
    String index() {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return "index";
        } else {
            return "redirect:/task";
        }
    }

}
