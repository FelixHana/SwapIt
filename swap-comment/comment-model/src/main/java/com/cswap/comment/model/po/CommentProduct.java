package com.cswap.comment.model.po;

import com.baomidou.mybatisplus.annotation.*;
import com.cswap.comment.model.dto.CommentDto;
import com.cswap.common.domain.dto.CommentUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment_product")
@Schema(description="商品评论表")
public class CommentProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @Schema(description = "评论类型 对商品/评论")
    @TableField("type")
    private Integer type;

    @Schema(description = "内容")
    @TableField("content")
    private String content;

    @Schema(description = "商品id")
    @TableField("product_id")
    private Long productId;

    @Schema(description = "父评论id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "对方用户id")
    @TableField("target_user_id")
    private Long targetUserId;

    @Schema(description = "评论用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "点赞数")
    @TableField("likes")
    private Integer likes;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public CommentDto convertToDto(CommentUserDto userDto) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(getCommentId());
        commentDto.setParentId(getParentId());
        commentDto.setUid(getUserId());
        commentDto.setContent(getContent());
        commentDto.setLikes(getLikes());
        commentDto.setCreateTime(getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentDto.setUser(userDto);
        return commentDto;
    }
}
