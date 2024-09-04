package com.cswap.common.exception;

import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import lombok.Getter;

/**
 * @author ZCY-
 */
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
