package com.cswap.auth.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 第三方账号表
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_oauth2_third_account")
@Schema(name="SysOAuth2ThirdAccount对象", description="第三方账号表")
public class SysOAuth2ThirdAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("unique_id")
    private String uniqueId;

    @TableField("platform")
    private String platform;

    @TableField("credential")
    private String credential;

    @TableField("credential_expire_time")
    private LocalDateTime credentialExpireTime;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String avatarUrl;


}
