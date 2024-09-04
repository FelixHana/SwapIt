package com.cswap.captcha.service.impl;

import com.cswap.captcha.service.CaptchaService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("MemoryCaptchaStore")
public class MemoryCaptchaStore implements CaptchaService.CaptchaStore {

    Map<String,String> map = new HashMap<>();

    @Override
    public void set(String key, String value, Integer expire) {
        map.put(key,value);
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }
}
