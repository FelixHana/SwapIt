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
@TableName("category_brand")
@Schema(description="品牌分类关联")
public class CategoryBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "品牌id")
    @TableField("brand_id")
    private Long brandId;

    @Schema(name = "分类id")
    @TableField("category_id")
    private Long categoryId;

    @Schema(name = "品牌名称")
    @TableField("brand_name")
    private String brandName;

    @Schema(name = "分类名称")
    @TableField("category_name")
    private String categoryName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;

}
