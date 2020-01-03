package com.ynthm.springbootdemo.infrastructure.bean.http;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : Ynthm
 */
@Data
public class Request {
    protected Map<String, String> headers = new HashMap<>();
}
