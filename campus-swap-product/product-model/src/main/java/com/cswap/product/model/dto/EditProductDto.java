package com.cswap.product.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.dto.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author ZCY-
 */
@EqualsAndHashCode
@Data
@Schema(name = "修改商品DTO")
public class EditProductDto {
    @Schema(description = "商品id")
    @NotNull
    private Long productId;

    @NotEmpty(message = "商品名不能为空")
    @Schema(description = "商品名")
    private String productName;

    @NotEmpty(message = "商品描述不能为空")
    @Schema(description = "商品描述")
    private String productDesc;

    @NotNull(message = "商品价格不能为空")
    @Schema(description = "价格")
    private Integer price;

    @NotEmpty(message = "商品类别不能为空")
    @Schema(description = "商品类目")
    private String category;

    @Valid
    @Schema(description = "商品图对象")
    private ProductImage images;

    @NotNull(message = "院校信息不能为空")
    @Schema(description = "所在院校")
    private Long universityId;

    @NotNull(message = "是否跨区域不能为空")
    @Schema(description = "是否跨院校交易")
    private Boolean isCrossCampus;


}
