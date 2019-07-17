package com.runke.model.entity;

import lombok.Data;
/**
 * 用户实体
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String userCode;
    private String pwd;
    private boolean state;
    private String email;
    private String avatar;
}
