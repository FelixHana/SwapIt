package com.cswap.auth.exception;

import com.cswap.common.exception.ApiException;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * @author ZCY-
 */
@Getter
public class AuthException extends AuthenticationException implements ApiException {
    private final AuthCodeEnum authCodeEnum;

    public AuthException(AuthCodeEnum anEnum) {
        super(anEnum.getMessage());
        this.authCodeEnum = anEnum;
    }

    public AuthException(AuthCodeEnum anEnum, String message) {
        super(anEnum.getMessage() + " : " + message);
        this.authCodeEnum = anEnum;
    }

    @Override
    public String getCode() {
        return authCodeEnum.getEnumName();
    }

}
