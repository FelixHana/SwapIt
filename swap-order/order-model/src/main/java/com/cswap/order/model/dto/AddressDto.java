package com.cswap.order.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="收货地址")
public class AddressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "收货人姓名")
    private String name;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "省份/直辖市")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "区")
    private String region;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "省市区代码")
    private String areaCode;

    @Schema(description = "是否默认")
    private Boolean defaultStatus;


}
