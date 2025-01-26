package com.cswap.order.model.dto;

import com.cswap.common.domain.enums.DeliveryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "订单提交DTO")
public class OrderSubmitDto {
    @NotNull(message = "收货地址不能为空")
    @Schema(description = "地址")
    private Long addressId;

    @NotNull(message = "商品不能为空")
    @Schema(description = "商品")
    private Long productId;

    @NotNull(message = "令牌不能为空")
    @Schema(description = "令牌")
    private String orderToken;

    @NotNull(message = "订单价格不能为空")
    @Schema(description = "价格")
    private BigDecimal payAmount;

}
