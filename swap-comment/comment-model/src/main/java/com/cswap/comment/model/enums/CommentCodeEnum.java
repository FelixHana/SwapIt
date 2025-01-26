package com.cswap.comment.model.enums;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ZCY-
 */

@Getter
public enum CommentCodeEnum implements BaseCodeEnum {
    COMMENT_EXIST(HttpStatus.OK, 40001, "评论已存在"),
    COMMENT_NOT_EXIST(HttpStatus.OK, 40002, "评论不存在"),


    COMMENT_CREATE_ERROR_DB(HttpStatus.OK, 40011, "评论创建失败-数据库错误"),


    COMMENT_DELETE_ERROR_DB(HttpStatus.OK, 40021, "评论删除失败-数据库错误"),


    COMMENT_UPDATE_ERROR_DB(HttpStatus.OK, 40031, "评论更新失败-数据库错误"),


    COMMENT_QUERY_ERROR_DB(HttpStatus.OK, 40041, "评论查询失败-数据库错误"),

    LIKE_ERROR_DB(HttpStatus.OK, 41011, "点赞失败-数据库错误");

    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;
    CommentCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }
}
