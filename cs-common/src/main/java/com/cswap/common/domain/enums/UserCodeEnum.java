package com.cswap.common.domain.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserCodeEnum implements BaseCodeEnum {
    USERNAME_EXIST(HttpStatus.OK, 10001, "用户名已存在"),
    USERNAME_NOT_EXIST(HttpStatus.OK, 10002, "用户名不存在"),
    AUTH_ERROR(HttpStatus.OK, 10003, "用户名或密码错误")

    ;

    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;
    UserCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }
}
