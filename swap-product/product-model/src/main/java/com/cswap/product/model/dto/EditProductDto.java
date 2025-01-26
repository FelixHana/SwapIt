package com.cswap.product.model.dto;


import com.cswap.common.domain.enums.DeliveryType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ZCY-
 */
@EqualsAndHashCode
@Data
@Schema(name = "修改商品DTO")
@Accessors(chain = true)
public class EditProductDto {
    @Schema(description = "商品id")
    private Long productId;

    @NotEmpty(message = "商品名不能为空")
    @Schema(description = "商品名")
    private String productName;

    @NotEmpty(message = "商品描述不能为空")
    @Schema(description = "商品描述")
    private String productDesc;

    @NotNull(message = "商品价格不能为空")
    @Schema(description = "价格")
    private BigDecimal price;

    @NotEmpty(message = "商品类别不能为空")
    @Schema(description = "商品类目")
    private String category;

//    @Valid
//    @Schema(description = "商品图对象")
//    private ProductImage images;

    @Schema(description = "图片url")
    private List<String> images;

    @NotNull(message = "自提选项不能为空")
    @Schema(description = "自提")
    private Boolean selfPickup;

    @NotNull(message = "发货方式不能为空")
    @Schema(description = "发货方式")
    private DeliveryType delivery;

    @Schema(description = "运费")
    private BigDecimal freight;

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "商品属性")
    private List<AttributeValues> attributes;

    @Data
    public static class AttributeValues {
        private Long attributeId;

        private String attributeName;

        private String attributeValue;
    }

}



