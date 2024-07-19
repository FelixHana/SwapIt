package com.place.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "user id PK")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_role")
    private Integer userRole;

    @TableField("name")
    private String name;

    @TableField("id_type")
    private Integer idType;

    @TableField("id_number")
    private String idNumber;

    @TableField("phone")
    private String phone;

    @TableField("user_level")
    private Integer userLevel;

    @TableField("user_profile")
    private String userProfile;

    @ApiModelProperty(value = "PK in sys_position")
    @TableField("location")
    private Integer location;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户盐值")
    @TableField("user_salt")
    private String userSalt;


}
