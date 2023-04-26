package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Pojo.User;
import com.webapp.webapp.Service.UserService;
import com.webapp.webapp.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param user 用户信息
     * @return 登陆成功返回jwt, 失败返回错误信息
     */
    @PostMapping ("/login")
    public Result login(@RequestBody User user) throws Exception{
        log.info("用户{}登录",user.getUserName());
        User loginUser = userService.login(user);
        //登录成功,生成令牌,下发令牌
        if (loginUser != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userName", loginUser.getUserName());
            claims.put("password", loginUser.getPassword());
            claims.put("id", loginUser.getId());
            claims.put("email", loginUser.getEmail());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册成功与否的Result
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) throws Exception{
        log.info("用户{}注册",user.getUserName());
        String msg = userService.register(user);
        //注册成功
        if (StringUtils.isEmpty(msg))
            return Result.success();
        //注册失败,返回错误信息
        return Result.error(msg);
    }
}
