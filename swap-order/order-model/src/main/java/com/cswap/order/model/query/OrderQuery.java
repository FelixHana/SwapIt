package com.cswap.order.model.query;


import com.cswap.common.domain.page.PageQuery;
import com.cswap.order.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "订单分页查询DTO", description = "订单分页查询DTO")
public class OrderQuery extends PageQuery{
    @Schema(description = "订单id")
    private Long orderId;
    @Schema(description = "商品id")
    private Long productId;
    @Schema(description = "买家id")
    private Long buyerId;
    @Schema(description = "卖家id")
    private Long sellerId;
    @Schema(description = "订单状态")
    private OrderStatus orderStatus;
    @Schema(description = "创建时间起始值")
    private LocalDateTime createTimeBegin;
    @Schema(description = "创建时间结束值")
    private LocalDateTime createTimeEnd;
}
