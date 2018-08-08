package com.ynthm.springbootdemo.infrastructure.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Author : Ynthm
 */
@Data
@AllArgsConstructor
public class UserVo implements Serializable {
    @NotEmpty(message = "name is empty.")
    private String username;

    @NotEmpty(message = "password is empty.")
    private String password;
}
