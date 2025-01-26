package com.cswap.product.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode
@Data
@Schema(name = "品牌DTO")
@Accessors(chain = true)
public class BrandDto {
    @Schema(description = "品牌id")
    private Long id;

    @NotEmpty(message = "品牌名不能为空")
    @Schema(description = "品牌名")
    private String name;

    @Schema(description = "品牌logo")
    private String logo;

    @NotNull(message = "品牌状态不能为空")
    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "检索首字母")
    private String firstLetter;

    @Schema(description = "排序")
    private Integer sort;

}
