package com.cswap.captcha.service;


import cn.hutool.core.util.StrUtil;
import com.cswap.captcha.model.CaptchaParamsDto;
import com.cswap.captcha.model.CaptchaResultDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCaptchaService implements CaptchaService {

    protected CaptchaGenerator captchaGenerator;
    protected KeyGenerator keyGenerator;
    protected CaptchaStore captchaStore;

    public abstract void setCaptchaGenerator(CaptchaGenerator captchaGenerator);

    public abstract void setKeyGenerator(KeyGenerator keyGenerator);

    public abstract void setCaptchaStore(CaptchaStore CaptchaStore);


    /**
     * @param captchaParamsDto 生成验证码参数
     * @param code_length      验证码长度
     * @param keyPrefix        key的前缀
     * @param expire           过期时间
     * @return
     * @description 生成验证公用方法
     * @author Mr.M
     * @date 2022/9/30 6:07
     */
    public GenerateResult generate(CaptchaParamsDto captchaParamsDto, Integer code_length, String keyPrefix, Integer expire) {
        //生成四位验证码
        String code = captchaGenerator.generate(code_length);
        log.debug("生成验证码:{}", code);
        //生成一个key
        String key = keyGenerator.generate(keyPrefix);

        //存储验证码
        captchaStore.set(key, code, expire);
        //返回验证码生成结果
        GenerateResult generateResult = new GenerateResult();
        generateResult.setKey(key);
        generateResult.setCode(code);
        return generateResult;
    }

    @Override
    public abstract CaptchaResultDto generate(CaptchaParamsDto captchaParamsDto);

    @Override
    public boolean verify(String key, String code) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
            return false;
        }
        String code_l = captchaStore.get(key);
        if (code_l == null) {
            return false;
        }
        boolean result = code_l.equalsIgnoreCase(code);
        if (result) {
            //删除验证码
            captchaStore.remove(key);
        }
        return result;
    }

    @Data
    protected class GenerateResult {
        String key;
        String code;
    }


}
