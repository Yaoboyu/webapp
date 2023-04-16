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
//        // 验证码开关
//        boolean captchaEnabled = configService.selectCaptchaEnabled();
//        if (captchaEnabled)
//        {
//            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
//        }

        if (StringUtils.isEmpty(userName))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (userName.length() < 2
                || userName.length() > 20)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < 5
                || password.length() > 20)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (!checkUserNameUnique(userName))
        {
            msg = "保存用户'" + userName + "'失败，注册账号已存在";
        }
        else
        {
//            registerUser.setPassword(SecurityUtils.encryptPassword(password));
            registerUser.setPassword(password);
            boolean regFlag = registerUser(registerUser);
            if (!regFlag)
                msg = "注册失败,请联系系统管理人员";
        }
        return msg;
    }

    //检查用户名是否唯一
    private boolean checkUserNameUnique(String userName) {
        return userMapper.GetUserByUserName(userName) == null;
    }

    //注册用户
    public boolean registerUser(User user)
    {
        return userMapper.InsertUser(user) > 0;
    }
}
