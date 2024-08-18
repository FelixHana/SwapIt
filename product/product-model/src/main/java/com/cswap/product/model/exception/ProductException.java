package com.cswap.product.model.exception;

import com.cswap.common.domain.enums.UserCodeEnum;
import com.cswap.common.exception.HttpException;
import com.cswap.product.model.enums.ProductCodeEnum;
import lombok.Getter;

/**
 * @author ZCY-
 */
@Getter
public class ProductException extends HttpException {
    private final ProductCodeEnum productCodeEnum;
    public ProductException(ProductCodeEnum anEnum) {
        super(anEnum.getHttpStatus());
        this.productCodeEnum = anEnum;
    }

    public ProductException(ProductCodeEnum anEnum, String message) {
        super(anEnum.getHttpStatus(), message);
        this.productCodeEnum = anEnum;
    }

    @Override
    public String getCode() {
        return productCodeEnum.getEnumName();
    }

}
