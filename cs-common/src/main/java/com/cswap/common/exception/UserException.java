package com.cswap.common.exception;

import com.cswap.common.domain.enums.UserCodeEnum;
import lombok.Getter;

@Getter
public class UserException extends HttpException{
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
