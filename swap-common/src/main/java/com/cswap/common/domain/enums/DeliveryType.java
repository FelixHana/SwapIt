package com.cswap.common.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author ZCY-
 */

@Getter
public enum DeliveryType {
    FREE_SHIPPING(0, "免邮"),
    FIXED_SHIPPING(1, "固定运费"),
    NO_SHIPPING(2, "无需邮寄/仅自提");

    @EnumValue
    private final int value;
    private final String desc;

    DeliveryType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
