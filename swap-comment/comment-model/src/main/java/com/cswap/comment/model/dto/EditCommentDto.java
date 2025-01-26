package com.cswap.comment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "评论DTO")
public class EditCommentDto {

    @NotEmpty(message = "类型不能为空")
    @Schema(description = "类型")
    private Integer type;

    @NotEmpty(message = "内容不能为空")
    @Schema(description = "内容")
    private String content;

    @NotNull
    @Schema(description = "评论对象id")
    private Long objectId;

    @NotNull(message = "父评论id不能为空")
    @Schema(description = "父评论id")
    private Long parentId;

    @NotEmpty(message = "对方用户不能为空")
    @Schema(description = "对方用户")
    private Long targetUserId;
}
