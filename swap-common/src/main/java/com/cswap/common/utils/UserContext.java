package com.cswap.common.utils;

import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;

public class UserContext {
    private static final ThreadLocal<Long> TL = new ThreadLocal<>();

    /**
     * 保存当前登录用户信息到ThreadLocal
     * @param userId 用户id
     */
    public static void setUser(Long userId) {
        TL.set(userId);
    }

    /**
     * 获取当前登录用户信息
     * @return 用户id
     */
    public static Long getUser() {
        Long userId = TL.get();
        if (userId == null){
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "用户未登录");
        }
        return userId;
    }

    /**
     * 移除当前登录用户信息
     */
    public static void removeUser(){
        TL.remove();
    }
}
