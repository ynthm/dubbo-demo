package com.ynthm.springbootdemo.domain.bean;

import javax.validation.constraints.NotEmpty;

/**
 * Author : Ynthm
 */
public class ValidationTestBean {

    @NotEmpty(message = "{user.name.notblank}")
    private String name;
}
