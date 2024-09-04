package com.cswap.auth.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ThirdAccountsPasswordGenerator {
    private static final String THIRD_PASSWORD_SECRET = "secret!secret!secret!123456";
    public static String generatePassword(String thirdPartyId) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(THIRD_PASSWORD_SECRET.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(thirdPartyId.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("生成密码失败", e);
        }
    }
}
