package com.cswap.auth.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Schema(description = "用户认证Dto")
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class UserAuthDto {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "类型")
    private String grant_type = "password";

}
