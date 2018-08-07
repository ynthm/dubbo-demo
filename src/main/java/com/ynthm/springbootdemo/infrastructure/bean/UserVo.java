package com.ynthm.springbootdemo.infrastructure.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Author : Ynthm
 */
@Data
public class UserVo {
    @NotEmpty(message = "name is empty.")
    private String username;

    @NotEmpty(message = "password is empty.")
    private String password;
}
