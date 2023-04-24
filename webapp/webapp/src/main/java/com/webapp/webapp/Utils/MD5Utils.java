package com.webapp.webapp.Utils;

import java.security.MessageDigest;

public class MD5Utils {

    /**
     * @Description: MD5加密
     * @Auther: ONESTAR
     * @Date: 17:19 2022/12/23
     * @Param: 要加密的字符串
     * @Return: 加密后的字符串
     */
    public static String code(String str) throws Exception{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 16位的加密
            return buf.toString().substring(8, 24);
    }
}