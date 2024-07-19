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
@TableName("response")
@ApiModel(value="Response对象", description="")
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "response id PK")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_response")
    private String userResponse;

    @TableField("response_detail")
    private String responseDetail;

    @TableField("response_file")
    private String responseFile;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("response_title")
    private String responseTitle;

    @TableField("schedule_date")
    private LocalDateTime scheduleDate;

    @TableField("request_id")
    private Long requestId;

    @ApiModelProperty(value = "0 pending, 1 accepted, 2 declined")
    @TableField("response_state")
    private Integer responseState;

    @TableField("cost")
    private Integer cost;


}
