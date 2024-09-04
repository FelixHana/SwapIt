package com.cswap.common.constant;


public final class CaptchaConstants {
    public static final String CAPTCHA_KEY_PREFIX = "captcha:";
    public static final int CAPTCHA_EXPIRE_TIME = 300;
    public static final int CAPTCHA_CODE_LENGTH = 4;
    public static final String CAPTCHA_PARAM_KEY = "captcha_key";
    public static final String CAPTCHA_PARAM_CODE = "captcha_code";
    public static final String CAPTCHA_SERVICE_HOST = "http://localhost:63085";
    public static final String CAPTCHA_SERVICE_VERIFY_URL = CAPTCHA_SERVICE_HOST + "/verify";

    private CaptchaConstants() {
        throw new IllegalStateException("Utility class");
    }
}
