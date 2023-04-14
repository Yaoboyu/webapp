package com.webapp.webapp.Utils;

import org.apache.commons.mail.HtmlEmail;

import java.util.Random;

public class MailUtils {
    /**
     * 用来发送邮件的工具类
     * @param EmailAdd 发件人邮箱
     * @param ToAdd 收件人邮箱
     * @param Au 发件人邮箱令牌,管邮箱公司要
     * @return 返回发的验证码
     * @throws Exception 我也不清楚有啥异常:(
     */
    public static int SendMail(String EmailAdd,String ToAdd,String Au) throws Exception{
/*        EmailAdd = ToAdd = "1712683212@qq.com";
        Au = "ezjcvxhvxxvmfajg";*/
        Random r = new Random(System.currentTimeMillis());
        int code = Math.abs(r.nextInt() % 10000);
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setCharset("utf-8");
        email.addTo(ToAdd);
        email.setFrom(EmailAdd);
        email.setAuthentication(EmailAdd,Au);
        email.setSubject("乌邦图科技:您好,请查收您的注册验证码");//设置发送主题
        email.setMsg("您的验证码是" + code + "请注意查收.");//设置发送内容
        email.send();//进行发送
        return code;
    }
}
