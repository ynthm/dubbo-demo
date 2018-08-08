package com.ynthm.springbootdemo.controller.restful;

import com.ynthm.springbootdemo.domain.bean.ValidationTestBean;
import com.ynthm.springbootdemo.infrastructure.exception.InternationalizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Author : Ynthm
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private MessageSource messageSource;

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

    @PostMapping("/valid")
    public ResponseEntity<?> messageResource(@Valid ValidationTestBean bean) {
        String responseStr = "OK";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseStr, HttpStatus.OK);
        return responseEntity;
    }


    @GetMapping("/exception")
    public ResponseEntity<?> exceptionResource() {
        Exception exception = new Exception("testERROR", new Throwable("E . R  . R . O . R"));

        InternationalizedException i18nException = new InternationalizedException(exception, "foo.test", "this default message", "fill param");
        log.error(i18nException.toString());
        log.error(i18nException.getLocalizedMessage(messageSource));
        log.error(i18nException.getLocalizedMessage(messageSource, Locale.US));


        String responseStr = "OK";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseStr, HttpStatus.OK);
        return responseEntity;
    }

}
