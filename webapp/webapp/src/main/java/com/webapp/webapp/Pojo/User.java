package com.webapp.webapp.Pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * User
 */
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户id，自增主键
     */
    private long userId;
    /**
     * 用户名
     */
    private String userName;
}