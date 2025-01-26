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
@TableName("brand")
@Schema(description="品牌")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "品牌id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "品牌名")
    @TableField("name")
    private String name;

    @Schema(description = "品牌logo")
    @TableField("logo")
    private String logo;

    @Schema(description = "显示状态 [0-不显示；1-显示]")
    @TableField("status")
    private Boolean status;

    @Schema(description = "检索首字母")
    @TableField("first_letter")
    private String firstLetter;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;


}
