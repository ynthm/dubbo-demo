package com.ynthm.springbootdemo.infrastructure.bean.http;


import lombok.Data;

/**
 * Author : Ynthm
 */
@Data
public class RawRequest extends Request {
    /**
     * json 格式数据
     */
    private String body;

    RawRequest() {
        this.headers.put(HttpHeader.CONTENT_TYPE, ContentType.JSON);
    }
}
