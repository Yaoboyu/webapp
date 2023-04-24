package com.webapp.webapp.Config;

import com.webapp.webapp.Exception.MyException;
import com.webapp.webapp.Pojo.User;
import org.springframework.stereotype.Component;

@Component
public class ThreadLocalConfig {

    private static ThreadLocal<User> localUser = new ThreadLocal<>();

    //获取当前线程的存的变量
    public static User getUser() {
        User user = localUser.get();
        if (null == user) {
            throw new MyException("登录失效，请重新登录！");
        }
        return user;
    }

    //设置当前线程的存的变量
    public static void setgetUser(User user) {
        localUser.set(user);
    }

    //移除当前线程的存的变量
    public static void remove() {
        localUser.remove();
    }

}
