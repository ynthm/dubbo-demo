package com.ynthm.springbootdemo.infrastructure.bean;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Author : Ynthm
 */
@Data
public class BaseResp<T> {

    private HttpStatus status;
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息描述
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private long currentTime;
}
