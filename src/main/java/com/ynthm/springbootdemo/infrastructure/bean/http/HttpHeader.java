package com.ynthm.springbootdemo.infrastructure.bean.http;

/**
 * Author : Ynthm
 */
public interface HttpHeader {
    //请求Header Accept
    String ACCEPT = "Accept";
    //请求Body内容MD5 Header
    String CONTENT_MD5 = "Content-MD5";
    //请求Header Content-Type
    String CONTENT_TYPE = "Content-Type";
    //请求Header UserAgent
    String USER_AGENT = "User-Agent";
    //请求Header Date
    String DATE = "Date";

}
