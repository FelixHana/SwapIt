package com.cswap.common.domain.enums.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ZCY-
 */

@Getter
public enum CommonCodeEnum implements BaseCodeEnum {
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, 1001, "Illegal Argument"),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, 1002, "Method Argument Not Valid"),
    BIND_ERROR(HttpStatus.BAD_REQUEST, 1003, "Bind Error"),
    AUTH_ERROR(HttpStatus.UNAUTHORIZED, 1004, "Auth Error"),
    INTERNAL_API(HttpStatus.FORBIDDEN, 1005, "Internal Api"),
    CAPTCHA_ERROR(HttpStatus.FORBIDDEN, 1006, "Captcha Error"),
    JWT_PARSE_ERROR(HttpStatus.FORBIDDEN, 1007, "Jwt Parse Error"),





    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 9999, "Internal Error")
    ;
    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;

    CommonCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }
    @Override
    public String getEnumName() {
        return this.name();
    }
}
