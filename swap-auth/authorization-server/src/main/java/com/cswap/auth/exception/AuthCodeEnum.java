package com.cswap.auth.exception;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ZCY-
 */

@Getter
public enum AuthCodeEnum implements BaseCodeEnum {
    CAPTCHA_ERROR(HttpStatus.UNAUTHORIZED, 90000, "Captcha Error"),
    CAPTCHA_PARAM_ERROR(HttpStatus.UNAUTHORIZED, 90001, "Captcha Param Error"),

    JWT_EXPIRED(HttpStatus.OK, 90002, "Jwt Expired"),
    JWT_INVALID(HttpStatus.OK, 90003, "Jwt Invalid"),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 99999, "Internal Error")
    ;
    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;

    AuthCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }
    @Override
    public String getEnumName() {
        return this.name();
    }
}
