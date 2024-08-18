package com.cswap.domain.query;

import com.cswap.common.domain.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户分页查询条件")
public class UserQuery extends PageQuery {
    @ApiModelProperty("搜索关键字 用户名")
    private String username;
    @ApiModelProperty("搜索关键字 电话号码")
    private String phone;
    @ApiModelProperty("创建时间最大值")
    private String createTimeBegin;
    @ApiModelProperty("创建时间最小值")
    private String createTimeEnd;


}
