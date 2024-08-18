package com.cswap.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户表单实体")
public class UserDTO {
    private String username;
    private Integer userRole;
    private String name;
    private Integer idType;
    private String idNumber;
    private String phone;
    private Integer userLevel;
    private String userProfile;
    private String location;
}
