package com.cswap.product.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("attribute")
@Schema(description="商品属性")
public class Attribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "属性id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "属性名")
    @TableField("name")
    private String name;

    @Schema(name = "是否检索 [0-不需要，1-需要]")
    @TableField("searchable")
    private Boolean searchable;

    @Schema(name = "可选值列表[用逗号分隔]")
    @TableField("value_list")
    private String valueList;

    @Schema(name = "所属分类")
    @TableField("category_id")
    private Long categoryId;

    @Schema(name = "启用状态 [0 - 禁用，1 - 启用]")
    @TableField("enable")
    private Boolean enable;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;

}
