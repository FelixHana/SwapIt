package com.cswap.comment.model.exception;

import com.cswap.common.exception.HttpException;
import com.cswap.comment.model.enums.CommentCodeEnum;
import lombok.Getter;

/**
 * @author ZCY-
 */
@Getter
public class CommentException extends HttpException {
    private final CommentCodeEnum commentCodeEnum;
    public CommentException(CommentCodeEnum anEnum) {
        super(anEnum.getHttpStatus());
        this.commentCodeEnum = anEnum;
    }

    public CommentException(CommentCodeEnum anEnum, String message) {
        super(anEnum.getHttpStatus(), message);
        this.commentCodeEnum = anEnum;
    }

    @Override
    public String getCode() {
        return commentCodeEnum.getEnumName();
    }

}
