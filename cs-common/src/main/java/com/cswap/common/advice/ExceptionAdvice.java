package com.cswap.common.advice;


import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.domain.response.ResultResponse;
import com.cswap.common.exception.CommonException;
import com.cswap.common.exception.HttpException;
import com.cswap.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.NestedServletException;

import java.net.BindException;
import java.util.stream.Collectors;

/**
 * @author ZCY-
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 自定义通用异常
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResultResponse<Void>> handleBadRequestException(CommonException e) {
        log.error("自定义公共异常 -> {} , 异常原因：{}  ", e.getClass().getName(), e.getMessage());
        log.debug("", e);
        return new ResponseEntity<>(ResultResponse.failed(e.getCommonCodeEnum(), e.getMessage()), e.getHttpStatus());
    }

    /**
     * 请求参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("|"));
        log.error("请求参数校验异常 -> {}", msg);
        CommonCodeEnum code = CommonCodeEnum.METHOD_ARGUMENT_NOT_VALID;
        return new ResponseEntity<>(ResultResponse.failed(code, "请求参数校验异常", msg), code.getHttpStatus());
    }
    /**
     * 请求参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResultResponse<Void>> handleBindException(BindException e) {
        log.error("请求参数绑定异常 ->BindException， {}", e.getMessage());
        HttpStatus status = CommonCodeEnum.BIND_ERROR.getHttpStatus();
        return new ResponseEntity<>(ResultResponse.failed(status, "请求参数格式错误"), status);
    }
    /**
     * 请求参数处理异常
     */
    @ExceptionHandler(NestedServletException.class)
    public ResponseEntity<ResultResponse<Void>> handleNestedServletException(NestedServletException e) {
        log.error("参数异常 -> NestedServletException，{}", e.getMessage());
        HttpStatus status = CommonCodeEnum.ILLEGAL_ARGUMENT.getHttpStatus();
        return new ResponseEntity<>(ResultResponse.failed(status, "请求参数处理异常"), status);
    }
    /**
     * 请求体异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求体异常 -> HttpMessageNotReadableException，{}", e.getMessage());
        CommonCodeEnum code = CommonCodeEnum.ILLEGAL_ARGUMENT;
        return new ResponseEntity<>(ResultResponse.failed(code, "请求体异常", e.getMessage()), code.getHttpStatus());
    }
    /**
     * 404 未找到资源
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResultResponse<Void>> handleHttpNotFoundException(NoHandlerFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ResultResponse.failed(status, e.getMessage()), status);
    }
    /**
     * 405 不支持的请求方法
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResultResponse<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return new ResponseEntity<>(ResultResponse.failed(status, e.getMessage()), status);
    }
    /**
     * Http通用异常
     */
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ResultResponse<Void>> handleUserException(HttpException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getHttpStatus(), e.getMessage()), e.getHttpStatus());
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
