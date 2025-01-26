package com.cswap.product.model.po;


import com.baomidou.mybatisplus.annotation.*;

import com.cswap.common.domain.enums.DeliveryType;
import com.cswap.common.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@TableName(value = "products", autoResultMap = true)
@Schema(name ="商品对象")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.AUTO)
    private Long productId;

    @TableField("seller_id")
    private Long sellerId;

    @TableField("product_name")
    private String productName;

    @TableField("product_desc")
    private String productDesc;

    @TableField("price")
    private BigDecimal price;

    @TableField("category")
    private String category;

    @TableField("product_status")
    private ProductStatus productStatus;

//    @TableField(value = "images", typeHandler = JacksonTypeHandler.class)
//    private ProductImage images;

    @TableField("self_pickup")
    private Boolean selfPickup;

    @TableField("delivery")
    private DeliveryType delivery;

    @TableField("freight")
    private BigDecimal freight;

    @TableField("brand_id")
    private Long brandId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("publish_time")
    private LocalDateTime publishTime;

    @TableLogic
    private Boolean deleted;


}
