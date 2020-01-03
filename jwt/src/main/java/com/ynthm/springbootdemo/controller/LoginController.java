package com.ynthm.springbootdemo.controller;

import com.ynthm.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Author : Ynthm
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public String getToken(String username, String password, HttpServletResponse response) {
        String authorization = userService.login(username, password);
        response.addHeader("Authorization", authorization);
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
