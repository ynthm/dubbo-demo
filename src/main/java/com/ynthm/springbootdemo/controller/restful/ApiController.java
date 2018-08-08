package com.ynthm.springbootdemo.controller.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : Ynthm
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @GetMapping("/test")
    public Authentication index(Authentication authentication) {
        log.info("resource: user {}", authentication);
        return authentication;
    }
}
