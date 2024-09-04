package com.cswap.order.model.exception;

import com.cswap.common.exception.HttpException;
import com.cswap.order.model.enums.OrderCodeEnum;
import lombok.Getter;

/**
 * @author ZCY-
 */
@Getter
public class OrderException extends HttpException {
    private final OrderCodeEnum orderCodeEnum;
    public OrderException(OrderCodeEnum anEnum) {
        super(anEnum.getHttpStatus());
        this.orderCodeEnum = anEnum;
    }

    public OrderException(OrderCodeEnum anEnum, String message) {
        super(anEnum.getHttpStatus(), message);
        this.orderCodeEnum = anEnum;
    }

    @Override
    public String getCode() {
        return orderCodeEnum.getEnumName();
    }

}
