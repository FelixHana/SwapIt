package com.cswap.product.model.enums;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @author ZCY-
 */

@Getter
public enum ProductStatus {
    AVAILABLE(0, "可购买"),
    SOLD(1, "已售出"),
    UNAVAILABLE(2, "已下架");
    @EnumValue
    private final int value;
    // TODO match name or value or desc in requests
    private final String desc;

    ProductStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
