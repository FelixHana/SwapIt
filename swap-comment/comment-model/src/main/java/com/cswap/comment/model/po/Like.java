package com.cswap.comment.model.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;



/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("like_record")
@Schema(description="文章/评论点赞记录表")
public class Like {

    @TableId(type = IdType.ASSIGN_ID)
    private Long likeId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "entity_type")
    private Integer entityType;

    @TableField(value = "entity_id")
    private Long entityId;

    @TableField(value = "like_status")
    private Boolean likeStatus;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
