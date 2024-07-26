package com.place.common.exception;

import com.place.common.domain.enums.CommonCodeEnum;
import lombok.Getter;

@Getter
public class CommonException extends HttpException {
    private final CommonCodeEnum commonCodeEnum;

    public CommonException(CommonCodeEnum anEnum) {
        super(anEnum.getHttpStatus());
        this.commonCodeEnum = anEnum;
    }

    public CommonException(CommonCodeEnum anEnum, String message) {
        super(anEnum.getHttpStatus(), message);
        this.commonCodeEnum = anEnum;
    }

    @Override
    public String getCode() {
        return commonCodeEnum.getEnumName();
    }

}
