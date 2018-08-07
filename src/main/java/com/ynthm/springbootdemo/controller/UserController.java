package com.ynthm.springbootdemo.controller;

import com.ynthm.springbootdemo.infrastructure.bean.UserVo;
import com.ynthm.springbootdemo.infrastructure.rbac.User;
import com.ynthm.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author : Ynthm
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public void login(@RequestBody UserVo userVo) {
        User user = new User();
        user.setName(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        userService.register(user);
    }

    @PostMapping(value = "/login")
    public String getToken(@RequestBody UserVo userVo) {
        return userService.login(userVo.getUsername(), userVo.getPassword());
    }

    @GetMapping(value = "/refreshToken")
    public String refreshToken(@RequestHeader String authorization) {
        return userService.refreshToken(authorization);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
