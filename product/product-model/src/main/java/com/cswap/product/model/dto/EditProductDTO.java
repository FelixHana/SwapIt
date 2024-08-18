package com.cswap.product.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author ZCY-
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "修改商品DTO")
public class EditProductDTO extends ProductDTO{
    @Schema(description = "商品id")
    @NotNull
    private Long productId;
}
