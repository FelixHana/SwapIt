package com.cswap.order.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.cswap.common.domain.enums.PaymentType;
import com.cswap.order.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zcy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
@Schema(description="订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单id")
    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private Long orderId;

    @Schema(description = "商品id")
    @TableField("product_id")
    private Long productId;

    @Schema(description = "买家id")
    @TableField("buyer_id")
    private Long buyerId;

    @Schema(description = "卖家id")
    @TableField("seller_id")
    private Long sellerId;

    @Schema(description = "订单状态")
    @TableField("order_status")
    private OrderStatus orderStatus;

    @Schema(description = "总价格")
    @TableField("total_price")
    private Integer totalPrice;

    @Schema(description = "付款方式")
    @TableField("payment_type")
    private PaymentType paymentType;

    @Schema(description = "买家已评价")
    @TableField("buyer_reviewed")
    private Boolean buyerReviewed;

    @Schema(description = "卖家已评价")
    @TableField("seller_reviewed")
    private Boolean sellerReviewed;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "付款时间")
    @TableField("payment_time")
    private LocalDateTime paymentTime;

    @Schema(description = "发货时间")
    @TableField("shipment_time")
    private LocalDateTime shipmentTime;

    @Schema(description = "完成时间")
    @TableField("complete_time")
    private LocalDateTime completeTime;

    @Schema(description = "关闭时间")
    @TableField("close_time")
    private LocalDateTime closeTime;

    @Schema(description = "评论时间")
    @TableField("comment_time")
    private LocalDateTime commentTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "逻辑删除字段")
    @TableField("deleted")
    private Boolean deleted;


}
