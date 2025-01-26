package com.cswap.product.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@EqualsAndHashCode
@Data
@Schema(name = "分类-品牌关联DTO")
@Accessors(chain = true)
public class CategoryBrandDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类-品牌关联id")
    private Long id;

    @NotNull
    @Schema(description = "品牌id")
    private Long brandId;

    @NotNull
    @Schema(description = "分类id")
    private String categoryId;

    @Schema(name = "品牌名称")
    @TableField("brand_name")
    private String brandName;

    @Schema(name = "分类名称")
    @TableField("category_name")
    private String categoryName;
}
