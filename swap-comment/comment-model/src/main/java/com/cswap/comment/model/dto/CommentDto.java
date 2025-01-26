package com.cswap.comment.model.dto;

import com.cswap.common.domain.dto.CommentUserDto;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZCY-
 */
@Data
public class CommentDto{

    Long id;

    Long parentId;

    Long uid;

    String content;

    Integer likes;

    String createTime;

    CommentUserDto user;

    Map<String, Object> reply;

}
