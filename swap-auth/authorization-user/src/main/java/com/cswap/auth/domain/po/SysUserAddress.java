package com.cswap.auth.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_address")
@Schema(description="收货地址")
public class SysUserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "收货人姓名")
    @TableField("name")
    private String name;

    @Schema(description = "电话")
    @TableField("phone")
    private String phone;

    @Schema(description = "省份/直辖市")
    @TableField("province")
    private String province;

    @Schema(description = "城市")
    @TableField("city")
    private String city;

    @Schema(description = "区")
    @TableField("region")
    private String region;

    @Schema(description = "详细地址")
    @TableField("detail_address")
    private String detailAddress;

    @Schema(description = "省市区代码")
    @TableField("area_code")
    private String areaCode;

    @Schema(description = "是否默认")
    @TableField("default_status")
    private Boolean defaultStatus;


}
