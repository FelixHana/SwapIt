package com.cswap.product.model.po;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_images")
@Schema(description="商品图片表")
public class ProductImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "商品id")
    @TableField("product_id")
    private Long productId;

    @Schema(description = "图片url")
    @TableField("url")
    private String url;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "默认图")
    @TableField("default_image")
    private Boolean defaultImage;

    @TableLogic("deleted")
    private Boolean deleted;


}
