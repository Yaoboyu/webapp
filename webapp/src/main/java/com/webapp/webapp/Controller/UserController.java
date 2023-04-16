package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Pojo.User;
import com.webapp.webapp.Service.UserService;
import com.webapp.webapp.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param user 用户信息
     * @return 登陆成功与否的Result
     */
    @PostMapping ("/login")
    public Result login(@RequestBody User user) throws Exception{
        log.info("用户{}登录",user.getUserName());
        User loginUser = userService.login(user);
        //登录成功,生成令牌,下发令牌
        if (loginUser != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getUserId());
            claims.put("userName", loginUser.getUserName());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) throws Exception{
        log.info("用户{}注册",user.getUserName());
        String msg = userService.register(user);
        if (StringUtils.isEmpty(msg)){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getUserId());
            claims.put("userName", user.getUserName());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error(msg);
    }
}
