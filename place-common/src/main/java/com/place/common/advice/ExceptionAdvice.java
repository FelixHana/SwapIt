package com.place.common.advice;


import com.place.common.domain.enums.CommonCodeEnum;
import com.place.common.domain.response.ResultResponse;
import com.place.common.exception.CommonException;
import com.place.common.exception.HttpException;
import com.place.common.exception.UserException;
import com.place.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import java.net.BindException;
import java.util.stream.Collectors;

/**
 * @author ZCY-
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

/*    @ExceptionHandler(DbException.class)
    public ErrorResponse handleDbException(DbException e) {
        log.error("mysql数据库操作异常 -> ", e);
        return new ErrorResponse(e.getAnEnum().getCode(), e.getMessage());
    }*/

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResultResponse<Void>> handleBadRequestException(CommonException e) {
        log.error("自定义公共异常 -> {} , 异常原因：{}  ", e.getClass().getName(), e.getMessage());
        log.debug("", e);
        return new ResponseEntity<>(ResultResponse.failed(e.getCommonCodeEnum(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("|"));
        log.error("请求参数校验异常 -> {}", msg);
        HttpStatus status = CommonCodeEnum.METHOD_ARGUMENT_NOT_VALID.getHttpStatus();
        return new ResponseEntity<>(ResultResponse.failed(status, msg), status);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResultResponse<Void>> handleBindException(BindException e) {
        log.error("请求参数绑定异常 ->BindException， {}", e.getMessage());
        HttpStatus status = CommonCodeEnum.BIND_ERROR.getHttpStatus();
        return new ResponseEntity<>(ResultResponse.failed(status, "请求参数格式错误"), status);
    }

    @ExceptionHandler(NestedServletException.class)
    public ResponseEntity<ResultResponse<Void>> handleNestedServletException(NestedServletException e) {
        log.error("参数异常 -> NestedServletException，{}", e.getMessage());
        HttpStatus status = CommonCodeEnum.ILLEGAL_ARGUMENT.getHttpStatus();
        return new ResponseEntity<>(ResultResponse.failed(status, "请求参数处理异常"), status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求体异常 -> HttpMessageNotReadableException，{}", e.getMessage());
        HttpStatus status = CommonCodeEnum.ILLEGAL_ARGUMENT.getHttpStatus();
        return new ResponseEntity<>(ResultResponse.failed(status, "请求体异常"), status);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResultResponse<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return new ResponseEntity<>(ResultResponse.failed(status, e.getMessage()), status);
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ResultResponse<Void>> handleUserException(HttpException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getHttpStatus(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResultResponse<Void>> handleUserException(UserException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getUserCodeEnum()), e.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<Void>> handleRuntimeException(Exception e) {
        if (WebUtils.getRequest() != null) {
            log.error("其他异常 uri : {} -> ", WebUtils.getRequest().getRequestURI(), e);
        }
        CommonCodeEnum codeEnum = CommonCodeEnum.INTERNAL_ERROR;
        return new ResponseEntity<>(ResultResponse.failed(codeEnum, "服务器内部异常"), codeEnum.getHttpStatus());
    }

}
