package com.cswap.auth.advice;


import com.cswap.auth.exception.UserException;
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
public class UserExceptionAdvice {
    /**
     * 用户服务异常
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResultResponse<Void>> handleUserException(UserException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getUserCodeEnum(), e.getMessage()), e.getHttpStatus());
    }
}
