package com.cswap.auth.domain.enums;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum UserCodeEnum implements BaseCodeEnum {
    USERNAME_EXIST(HttpStatus.OK, 30001, "用户名已存在"),
    USER_NOT_EXIST(HttpStatus.OK, 30002, "用户不存在"),
    AUTH_ERROR(HttpStatus.OK, 30003, "用户名或密码错误"),
    PASSWORD_CONFIRM_ERROR(HttpStatus.OK, 30004, "两次输入的密码不一致"),
    MOBILE_EXIST(HttpStatus.OK, 30005, "手机号已存在"),


    USER_CREATE_ERROR_DB(HttpStatus.OK, 30011, "用户创建失败-数据库错误"),

    USER_DELETE_ERROR_(HttpStatus.OK, 30021, ""),

    USER_UPDATE_ERROR_DB(HttpStatus.OK, 30031, "用户更新失败-数据库错误"),







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
