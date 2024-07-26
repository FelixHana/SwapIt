package com.place.common.exception;

import com.place.common.domain.enums.CommonCodeEnum;
import com.place.common.domain.enums.UserCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
