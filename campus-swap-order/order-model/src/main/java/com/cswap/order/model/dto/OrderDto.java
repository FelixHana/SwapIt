package com.cswap.order.model.dto;

import com.cswap.common.domain.enums.PaymentType;
import com.cswap.order.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "订单DTO")
public class OrderDto {
    @NotNull(message = "订单状态不能为空")
    @Schema(description = "状态")
    private OrderStatus orderStatus;

    @NotNull(message = "订单价格不能为空")
    @Schema(description = "价格")
    private Integer totalPrice;

    @NotNull(message = "订单支付类型不能为空")
    @Schema(description = "支付类型")
    private PaymentType paymentType;

    @NotNull(message = "订单创建时间不能为空")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    private LocalDateTime paymentTime;

    @Schema(description = "发货时间")
    private LocalDateTime shipmentTime;

    @Schema(description = "完成时间")
    private LocalDateTime completeTime;
}
