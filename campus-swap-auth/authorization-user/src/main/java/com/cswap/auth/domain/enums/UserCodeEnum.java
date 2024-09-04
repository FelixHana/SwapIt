package com.cswap.auth.domain.enums;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum UserCodeEnum implements BaseCodeEnum {
    USERNAME_EXIST(HttpStatus.OK, 10001, "用户名已存在"),
    USERNAME_NOT_EXIST(HttpStatus.OK, 10002, "用户名不存在"),
    AUTH_ERROR(HttpStatus.OK, 10003, "用户名或密码错误"),


    USER_CREATE_ERROR_DB(HttpStatus.OK, 10011, "用户创建失败-数据库错误"),

    USER_DELETE_ERROR_(HttpStatus.OK, 10021, ""),

    USER_UPDATE_ERROR_DB(HttpStatus.OK, 10031, "用户更新失败-数据库错误"),







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
