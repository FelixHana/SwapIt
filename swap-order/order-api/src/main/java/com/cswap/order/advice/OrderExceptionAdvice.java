package com.cswap.order.advice;

import com.cswap.common.domain.response.ResultResponse;
import com.cswap.order.model.exception.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ZCY-
 */
@RestControllerAdvice
@Slf4j
public class OrderExceptionAdvice {
    /**
     * 订单服务异常
     */
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ResultResponse<Void>> handleProductException(OrderException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getOrderCodeEnum(), e.getMessage()), e.getHttpStatus());
    }
}
