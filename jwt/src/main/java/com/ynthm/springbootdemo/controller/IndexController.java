package com.ynthm.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author : Ynthm
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }


    @GetMapping("/upload")
    public String upload() {
        return "uploadForm";
    }
}
