package com.cswap.common.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author ZCY-
 */

@Getter
public enum PaymentType {
    CASH_ON_DELIVERY(0, "线下自提付款"),
    ONLINE_PAYMENT(1, "线上支付");

    @EnumValue
    private final int value;
    // TODO match name or value or desc in requests
    private final String desc;

    PaymentType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
