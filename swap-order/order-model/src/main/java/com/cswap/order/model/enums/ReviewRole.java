package com.cswap.order.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author ZCY-
 */
@Getter
public enum ReviewRole {
    BUYER(0, "买家"),
    SELLER(1, "卖家");

    @EnumValue
    private final int value;
    private final String desc;

    ReviewRole(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
