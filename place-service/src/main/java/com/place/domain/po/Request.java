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
@TableName("request")
@ApiModel(value="Request对象", description="")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "request id PK")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "username in user")
    @TableField("user_request")
    private String userRequest;

    @TableField("request_type")
    private Integer requestType;

    @TableField("request_title")
    private String requestTitle;

    @TableField("request_detail")
    private String requestDetail;

    @TableField("request_file")
    private String requestFile;

    @TableField("max_cost")
    private Integer maxCost;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("request_state")
    private Integer requestState;

    @TableField("location")
    private String location;


}
