package com.ynthm.springbootdemo.infrastructure.constant;

/**
 * Author : Ynthm
 */
public enum ResultCode {

    // -1为通用失败（根据ApiResult.java中的构造方法注释而来）
    FAIL(-1, "common fail"),
    // 0为成功
    SUCCESS(0, "success");


    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
