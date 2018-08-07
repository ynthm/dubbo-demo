package com.ynthm.springbootdemo.infrastructure.bean.http;

/**
 * Author : Ynthm
 */
public interface ContentType {
    //表单类型Content-Type
    String FORM = "application/x-www-form-urlencoded; charset=UTF-8";
    // 流类型Content-Type
    String STREAM = "application/octet-stream; charset=UTF-8";
    //JSON类型Content-Type
    String JSON = "application/json; charset=UTF-8";
    //XML类型Content-Type
    String XML = "application/xml; charset=UTF-8";
    //文本类型Content-Type
    String TEXT = "application/text; charset=UTF-8";
}
