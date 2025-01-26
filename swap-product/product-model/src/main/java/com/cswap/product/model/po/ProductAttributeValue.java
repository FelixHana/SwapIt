package com.cswap.product.model.po;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_attribute_value")
@Schema(description="商品-属性表")
public class ProductAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "商品id")
    @TableField("product_id")
    private Long productId;

    @Schema(description = "属性id")
    @TableField("attribute_id")
    private Long attributeId;

    @Schema(description = "属性名")
    @TableField("attribute_name")
    private String attributeName;

    @Schema(description = "属性值")
    @TableField("attribute_value")
    private String attributeValue;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;




}
