package com.cswap.captcha.service.impl;

import com.cswap.captcha.service.CaptchaService;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component("UUIDKeyGenerator")
public class UUIDKeyGenerator implements CaptchaService.KeyGenerator {
    @Override
    public String generate(String prefix) {
        String uuid = UUID.randomUUID().toString();
        return prefix + uuid.replaceAll("-", "");
    }
}
