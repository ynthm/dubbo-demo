package com.ynthm.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : Ynthm
 */
@RestController
@Slf4j
public class LogTestController {

    @GetMapping("/log")
    public void log() {
        log.info("info message.");
        log.error("error message.");
        log.debug("debug message.");
    }

    @Profile("prod")
    @GetMapping("/log/prod")
    public void logProd() {
        log.info("info message.");
        log.debug("debug message.");
    }

}
