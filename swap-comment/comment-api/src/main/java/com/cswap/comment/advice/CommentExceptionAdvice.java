package com.cswap.comment.advice;


import com.cswap.comment.model.exception.CommentException;
import com.cswap.common.domain.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ZCY-
 */
@RestControllerAdvice
@Slf4j
public class CommentExceptionAdvice {
    /**
     * 商品服务异常
     */
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ResultResponse<Void>> handleProductException(CommentException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getCommentCodeEnum(), e.getMessage()), e.getHttpStatus());
    }
}
