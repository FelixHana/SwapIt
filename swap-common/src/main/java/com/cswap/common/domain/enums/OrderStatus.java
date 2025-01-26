package com.cswap.common.domain.enums;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author ZCY-
 */

@Getter
public enum OrderStatus {
    PENDING_PAYMENT(0, "待付款"),
    PAID(1, "已付款"),
    SHIPPED(2, "已发货"),
    COMPLETED(3, "已收货"),
    CANCELED(4, "已取消")
    ;
    @EnumValue
    private final int value;
    // TODO match name or value or desc in requests
    private final String desc;

    OrderStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
