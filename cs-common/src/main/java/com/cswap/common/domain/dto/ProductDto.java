package com.cswap.common.domain.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "商品DTO")
public class ProductDto {

    @NotEmpty(message = "商品名不能为空")
    @TableField("product_name")
    @Schema(description = "商品名")
    private String productName;

    @NotEmpty(message = "商品描述不能为空")
    @Schema(description = "商品描述")
    @TableField("product_desc")
    private String productDesc;

    @NotNull
    @Schema(description = "商品卖家")
    @TableField("seller_id")
    private Long sellerId;

    @NotNull(message = "商品价格不能为空")
    @Schema(description = "价格")
    @TableField("price")
    private Integer price;

    @NotEmpty(message = "商品类别不能为空")
    @Schema(description = "商品类目")
    @TableField("category")
    private String category;

    @TableField(value = "images", typeHandler = JacksonTypeHandler.class)
    @Valid
    @Schema(description = "商品图对象")
    private ProductImage images;

    @NotNull(message = "院校信息不能为空")
    @Schema(description = "所在院校")
    @TableField("university_id")
    private Long universityId;

    @NotNull(message = "是否跨区域不能为空")
    @Schema(description = "是否跨院校交易")
    @TableField("is_cross_campus")
    private Boolean isCrossCampus;

}
