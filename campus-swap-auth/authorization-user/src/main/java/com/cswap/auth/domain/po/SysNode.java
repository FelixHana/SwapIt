package com.cswap.auth.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统功能表
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_node")
@Schema(name="SysNode对象", description="系统功能表")
public class SysNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "功能id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "权限名")
    @TableField("name")
    private String name;

    @TableField("url")
    private String url;

    @Schema(description = "类型 ")
    @TableField("type")
    private Integer type;

    @Schema(description = "父节点")
    @TableField("parent_id")
    private Long parentId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("deleted")
    private Boolean deleted;


}
