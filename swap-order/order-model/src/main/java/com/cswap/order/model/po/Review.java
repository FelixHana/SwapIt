package com.cswap.order.model.po;


import com.baomidou.mybatisplus.annotation.*;
import com.cswap.order.model.enums.ReviewRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reviews")
@Schema(description = "订单评价")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "review_id", type = IdType.ASSIGN_ID)
    private Long reviewId;

    @TableField("order_id")
    private Long orderId;

    @TableField("reviewer_id")
    private Long reviewerId;

    @TableField("review_role")
    private ReviewRole reviewRole;

    @TableField("rating")
    private Integer rating;

    @TableField("content")
    private String content;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "逻辑删除字段")
    @TableField("deleted")
    private Boolean deleted;


}
