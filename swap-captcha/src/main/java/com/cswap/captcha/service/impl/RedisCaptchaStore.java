package com.cswap.captcha.service.impl;

import com.cswap.captcha.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("RedisCaptchaStore")
@RequiredArgsConstructor
public class RedisCaptchaStore implements CaptchaService.CaptchaStore {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value, Integer expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
