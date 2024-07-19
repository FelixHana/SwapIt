package com.place.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "order id PK")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("request_id")
    private Long requestId;

    @ApiModelProperty(value = "PK in user")
    @TableField("user_request")
    private String userRequest;

    @ApiModelProperty(value = "PK in user")
    @TableField("user_response")
    private String userResponse;

    @TableField("order_time")
    private LocalDateTime orderTime;

    @TableField("request_fee")
    private Integer requestFee;

    @TableField("response_fee")
    private Integer responseFee;

    @TableField("response_id")
    private Long responseId;

    @TableField("location")
    private String location;

    @TableField("type")
    private Integer type;


}
