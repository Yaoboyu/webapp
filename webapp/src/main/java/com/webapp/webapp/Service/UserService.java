package com.webapp.webapp.Service;

import com.webapp.webapp.Pojo.User;

public interface UserService {

    /**
     * 用户登录
     * @param user 用户信息
     * @return 用户信息
     */
    User login(User user) throws Exception;

    /**
     * 用户注册
     * @param user 用户信息
     * @return 用户信息
     */
    String register(User user) throws Exception;
}
