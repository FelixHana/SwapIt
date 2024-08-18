package com.cswap.utils.salt;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SaltUtil {

    /**
     * 随机生成盐值工具
     * @param n times
     * @return stringBuilder
     */
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /**
     * 随机生成盐值，对用户密码进行加密
     * @param password 设置的密码
     * @return salt & password MD5
     */
    public static List<String> encryption(String password){

        List<String> msg = new ArrayList<>();
        String salt = SaltUtil.getSalt(10);
        msg.add(salt);

        Md5Hash MD5 = new Md5Hash(password, salt, 1024);
        msg.add(MD5.toHex());

        return msg;
    }
}
