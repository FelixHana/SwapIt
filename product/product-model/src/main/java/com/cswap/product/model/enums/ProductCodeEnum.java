package com.cswap.product.model.enums;

import com.cswap.common.domain.enums.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ZCY-
 */

@Getter
public enum ProductCodeEnum implements BaseCodeEnum {
    PRODUCT_EXIST(HttpStatus.OK, 10001, "商品已存在"),
    PRODUCT_NOT_EXIST(HttpStatus.OK, 10002, "商品不存在"),
    PRODUCT_SOLD(HttpStatus.OK, 10003, "商品已售出"),
    PRODUCT_NOT_AVAILABLE(HttpStatus.OK, 10004, "商品不可售"),




    ;

    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;
    ProductCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }
}
