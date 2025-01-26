package com.cswap.captcha.service;

import com.cswap.captcha.model.CaptchaParamsDto;
import com.cswap.captcha.model.CaptchaResultDto;

public interface CaptchaService {


    /**
     * @description 生成验证码
     * @param captchaParamsDto 生成验证码参数
     * @author Mr.M
     * @date 2022/9/29 18:21
    */
     CaptchaResultDto generate(CaptchaParamsDto captchaParamsDto);

     /**
      * @description 校验验证码
      * @param key
      * @param code
      * @return boolean
      * @author Mr.M
      * @date 2022/9/29 18:46
     */
     boolean verify(String key, String code);


    /**
     * @description 验证码生成器
     * @author Mr.M
     * @date 2022/9/29 16:34
    */
    interface CaptchaGenerator {
        /**
         * 验证码生成
         * @return 验证码
         */
        String generate(int length);
        

    }

    /**
     * @description key生成器
     * @author Mr.M
     * @date 2022/9/29 16:34
     */
    interface KeyGenerator{

        /**
         * key生成
         * @return 验证码
         */
        String generate(String prefix);
    }


    /**
     * @description 验证码存储
     * @author Mr.M
     * @date 2022/9/29 16:34
     */
    interface CaptchaStore {

        /**
         * @description 向缓存设置key
         * @param key key
         * @param value value
         * @param expire 过期时间,单位秒
         * @return void
         * @author Mr.M
         * @date 2022/9/29 17:15
        */
        void set(String key, String value, Integer expire);

        String get(String key);

        void remove(String key);
    }
}
