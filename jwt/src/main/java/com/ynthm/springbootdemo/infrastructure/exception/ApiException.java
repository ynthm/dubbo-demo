package com.ynthm.springbootdemo.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author : Ynthm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException {

    protected Long errorCode ;
    protected Object data ;

    public ApiException(Long errorCode,String message,Object data,Throwable e){
        super(message,e);
        this.errorCode = errorCode ;
        this.data = data ;
    }

    public ApiException(Long errorCode,String message,Object data){
        this(errorCode,message,data,null);
    }

    public ApiException(Long errorCode,String message){
        this(errorCode,message,null,null);
    }

    public ApiException(String message,Throwable e){
        this(null,message,null,e);
    }

    public ApiException(){

    }

    public ApiException(Throwable e){
        super(e);
    }
}
