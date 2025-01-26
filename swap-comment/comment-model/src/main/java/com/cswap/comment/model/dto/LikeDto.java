package com.cswap.comment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "点赞DTO")
public class LikeDto {
    @Schema(description = "点赞实体类型")
    private Integer entityType;

    @Schema(description = "点赞实体id")
    private Long entityId;
}
