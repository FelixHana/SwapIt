package com.cswap.common.domain.mq;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cswap.common.domain.enums.DeliveryType;
import com.cswap.common.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "订单号")
    private String orderSn;

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "买家id")
    private Long buyerId;

    @Schema(description = "卖家id")
    private Long sellerId;

    @Schema(description = "订单状态")
    private OrderStatus orderStatus;

    @Schema(description = "总价格")
    private BigDecimal totalAmount;

    @Schema(description = "应付价格")
    private BigDecimal payAmount;

    @Schema(description = "运费")
    private BigDecimal freightAmount;

    @Schema(description = "付款方式")
    private DeliveryType deliveryType;

    @Schema(description = "收货地址")
    private Long addressId;
}
