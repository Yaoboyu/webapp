package com.webapp.webapp.Service.impl;

import com.webapp.webapp.Mapper.UserMapper;
import com.webapp.webapp.Pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements com.webapp.webapp.Service.UserService{

    @Autowired
    UserMapper userMapper;

    //登录
    @Override
    public User login(User user) {
        return userMapper.GetUserByUserNameAndPassword(user);
    }
    //注册
    @Override
    public String register(User user) {
        String msg = "", userName = user.getUserName(), password = user.getPassword(),email = user.getEmail();
        User registerUser = new User();
        registerUser.setUserName(userName);
        if (!checkUserNameUnique(userName))
            msg = "保存用户'" + userName + "'失败，注册账号已存在";
        else
        {
//            registerUser.setPassword(SecurityUtils.encryptPassword(password));
            registerUser.setPassword(password);
            userMapper.InsertUser(registerUser);
        }
        return msg;
    }

    //检查用户名是否唯一
    private boolean checkUserNameUnique(String userName) {
        return userMapper.GetUserByUserName(userName) == null;
    }
}
