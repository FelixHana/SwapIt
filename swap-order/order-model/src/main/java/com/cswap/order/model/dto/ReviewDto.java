package com.cswap.order.model.dto;

import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.enums.ReviewRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "订单评价DTO")
public class ReviewDto {
    @NotNull(message = "订单id不能为null")
    @Schema(description = "订单id")
    private Long orderId;

    @NotNull(message = "评价身份不能为null")
    @Schema(description = "身份")
    private ReviewRole reviewRole;

    @NotNull(message = "评价分数不能为null")
    @Schema(description = "分数")
    private Integer rating;

    @NotNull(message = "评价内容不能为null")
    @Schema(description = "内容")
    private String content;
}
