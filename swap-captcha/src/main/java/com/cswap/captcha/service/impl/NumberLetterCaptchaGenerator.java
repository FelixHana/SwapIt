package com.cswap.captcha.service.impl;

import com.cswap.captcha.service.CaptchaService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("NumberLetterCaptchaGenerator")
public class NumberLetterCaptchaGenerator implements CaptchaService.CaptchaGenerator {

    @Override
    public String generate(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
