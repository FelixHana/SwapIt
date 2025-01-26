package com.cswap.product.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品属性DTO")
public class AttributeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "属性id")
    private Long id;

    @Schema(description = "属性名")
    private String name;

    @Schema(description = "是否检索 [0-不需要，1-需要]")
    private Boolean searchable;

    @Schema(description = "可选值列表[用逗号分隔]")
    private String valueList;

    @Schema(description = "所属分类")
    private Long categoryId;

    @Schema(description = "启用状态 [0 - 禁用，1 - 启用]")
    private Boolean enable;
}
