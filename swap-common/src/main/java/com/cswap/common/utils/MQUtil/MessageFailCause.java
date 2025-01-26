package com.cswap.common.utils.MQUtil;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum MessageFailCause {
    PRODUCER_FAILED(0, "发送端失败"),
    QUEUING_FAILED(1, "投递失败"),
    CONSUMER_FAILED(2, "消费失败");

    @EnumValue
    private final int value;
    private final String desc;

    MessageFailCause(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
