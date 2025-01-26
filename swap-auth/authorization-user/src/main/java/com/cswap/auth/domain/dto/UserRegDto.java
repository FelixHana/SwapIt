package com.cswap.auth.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * @author ZCY-
 */
@Schema(description = "用户注册Dto")
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class UserRegDto {
    @Schema(description = "账号名")
    private String account;
    @Schema(description = "昵称")
    private String name;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "密码确认")
    private String confirmPassword;
    @Schema(description = "电话号码")
    @Length(max = 11, min = 11, message = "手机号长度为11位")
    private String mobile;
    @Schema(description = "验证码key")
    private String captchaKey;
    @Schema(description = "验证码code")
    private String captchaCode;
}
