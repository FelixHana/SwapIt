package com.cswap.auth.exception;


import com.cswap.auth.domain.enums.UserCodeEnum;
import com.cswap.common.exception.HttpException;
import lombok.Getter;

@Getter
public class UserException extends HttpException {
    private final UserCodeEnum userCodeEnum;
    public UserException(UserCodeEnum anEnum) {
        super(anEnum.getHttpStatus());
        this.userCodeEnum = anEnum;
    }

    public UserException(UserCodeEnum anEnum, String message) {
        super(anEnum.getHttpStatus(), message);
        this.userCodeEnum = anEnum;
    }

    @Override
    public String getCode() {
        return userCodeEnum.getEnumName();
    }

}
