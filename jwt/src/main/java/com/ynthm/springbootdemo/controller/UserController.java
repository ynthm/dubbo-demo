package com.ynthm.springbootdemo.controller;

import com.ynthm.springbootdemo.infrastructure.bean.UserVo;
import com.ynthm.springbootdemo.infrastructure.rbac.User;
import com.ynthm.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Author : Ynthm
 */
@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public void register(@RequestBody UserVo userVo) {
        User user = new User();
        user.setName(userVo.getUsername());
        user.setPassword(userVo.getPassword());

        // result.rejectValue("name", "misFormat", "这个name已经注册过了！");
        userService.register(user);
    }

    @PostMapping(value = "/login")
    public String getToken(@Valid UserVo userVo, HttpServletResponse response, Model model, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                log.warn(error.toString());
            }
            model.addAttribute("user", userVo);
            return "/login";
        }
        String authorization = userService.login(userVo.getUsername(), userVo.getPassword());
        response.addHeader("Authorization", authorization);
        return "redirect:/index";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "${jwt.route.authentication.refresh}")
    public String refreshToken(@RequestHeader String authorization) {
        return userService.refreshToken(authorization);
    }
}
