package com.cswap.common.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.cswap.common.constant.SecurityConstants;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;

import java.util.Map;

/**
 * @author ZCY-
 */
public class JwtUtil {


    public static JWTPayload parsePayload(String bearerToken) {
        if (StrUtil.isNotBlank(bearerToken) && StrUtil.startWithIgnoreCase(bearerToken, SecurityConstants.TOKEN_PREFIX)) {
            String token = bearerToken.substring(SecurityConstants.TOKEN_PREFIX.length());
            try {
                JWTPayload payload = JWTUtil.parseToken(token).getPayload();
                if (payload == null){
                    throw new CommonException(CommonCodeEnum.JWT_PARSE_ERROR);
                }
                return payload;
            } catch (Exception e) {
                throw new CommonException(CommonCodeEnum.JWT_PARSE_ERROR);
            }
        } else {
            return null;
        }

    }

}
