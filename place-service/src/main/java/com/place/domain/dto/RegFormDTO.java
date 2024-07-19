package com.place.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "登录表单实体")
public class RegFormDTO {
    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @NotNull(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
    @NotNull(message = "地区不能为空")
    @ApiModelProperty(value = "地区", required = true)
    private String location;





}
