package com.ynthm.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author : Ynthm
 */
@Controller
public class IdController {

    @GetMapping("/test")
    public String index()
    {
        return "uploadForm";
    }

    @GetMapping("/index")
    public String upload()
    {
        return "redirect:/index.html";
    }
}
