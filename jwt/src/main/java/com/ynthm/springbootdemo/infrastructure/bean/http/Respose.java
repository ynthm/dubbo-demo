package com.ynthm.springbootdemo.infrastructure.bean.http;

import lombok.Data;
import java.util.Map;
/**
 * Author : Ynthm
 */
@Data
public class Respose {
    private int statusCode;
    private String contentType;
    private String requestId;
    private String errorMessage;
    private Map<String, String> headers;
    private String body;
}
