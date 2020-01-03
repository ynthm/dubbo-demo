package com.ynthm.springbootdemo.controller;

import com.ynthm.springbootdemo.domain.bean.ValidationTestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Author : Ynthm
 */
@RestController
@Slf4j
public class LogTestController {

    @GetMapping("/log")
    public ResponseEntity<?> log() {
        log.debug("debug message.");
        log.info("info message.");
        log.error("error message.");
        log.warn("warn message.");

        String responseStr = "OK";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseStr, HttpStatus.OK);
        return responseEntity;
    }

    @Profile("prod")
    @GetMapping("/log/prod")
    public void logProd() {
        log.info("info message.");
        log.debug("debug message.");
    }


    @PostMapping("/valid/test")
    public ResponseEntity<?> messageResource(@Valid  ValidationTestBean bean) {
        String responseStr = "OK";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseStr, HttpStatus.OK);
        return responseEntity;
    }

}
