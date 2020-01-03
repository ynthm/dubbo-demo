package com.ynthm.springbootdemo.infrastructure.bean.http;


import lombok.Data;

import java.util.Map;

/**
 * Author : Ynthm
 */
@Data
public class FormRequest extends Request {
    private Map<String, String> body;
}
