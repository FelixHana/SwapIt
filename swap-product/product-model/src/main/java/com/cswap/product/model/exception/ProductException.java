package com.cswap.product.model.exception;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import com.cswap.common.exception.HttpException;
import com.cswap.product.model.enums.ProductCodeEnum;
import com.cswap.product.model.po.Brand;
import lombok.Getter;

/**
 * @author ZCY-
 */
@Getter
public class ProductException extends HttpException {
    private final BaseCodeEnum baseCodeEnum;

    public ProductException(ProductCodeEnum anEnum) {
        super(anEnum.getHttpStatus());
        this.baseCodeEnum = anEnum;
    }


    public ProductException(ProductCodeEnum anEnum, String message) {
        super(anEnum.getHttpStatus(), message);
        this.baseCodeEnum = anEnum;
    }



    @Override
    public String getCode() {
        return baseCodeEnum.getEnumName();
    }

}
