package com.webapp.webapp.Filter;

import cn.hutool.core.bean.BeanUtil;
import com.webapp.webapp.Config.ThreadLocalConfig;
import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Pojo.User;
import com.webapp.webapp.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 请求过滤器
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class Filter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //1.获取请求url。
        String url = req.getRequestURL().toString();
        log.info("请求的url: {}",url);

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if(url.contains("login") || url.contains("register")){
            log.info("登录操作, 放行...");
            chain.doFilter(request,response);
            return;
        }
        //axios默认发两遍请求所以第一遍预处理给予放行
        if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            chain.doFilter(request,response);
            return;
        }
        //3.获取请求头中的令牌（token）。
        String jwt = req.getHeader("token");
        String u = req.getHeader("User-Agent");
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        //利用map转bean工具实现线程变量维护
        try {
            Claims claims = JwtUtils.parseJWT(jwt);
            Map<String,Object> map = new HashMap<>(claims);
            User user = BeanUtil.fillBeanWithMap(map, new User(), false);
            ThreadLocalConfig.setgetUser(user);
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败, 返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }

        //6.放行。
        log.info("令牌合法, 放行");
        chain.doFilter(request, response);
        ThreadLocalConfig.remove();//释放线程变量
    }
}
