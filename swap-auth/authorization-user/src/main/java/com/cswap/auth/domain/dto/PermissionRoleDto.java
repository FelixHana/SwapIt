package com.cswap.auth.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
public class PermissionRoleDto {
    private Long id;
    private String name;
    private String url;
    private String method;
    @TableField(exist = false, typeHandler = JacksonTypeHandler.class)
    private List<String> roles;
}
