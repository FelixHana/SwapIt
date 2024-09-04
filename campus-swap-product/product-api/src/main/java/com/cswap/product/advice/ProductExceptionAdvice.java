package com.cswap.product.advice;

import com.cswap.common.domain.response.ResultResponse;
import com.cswap.product.model.exception.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ZCY-
 */
@RestControllerAdvice
@Slf4j
public class ProductExceptionAdvice {
    /**
     * 商品服务异常
     */
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ResultResponse<Void>> handleProductException(ProductException e) {
        return new ResponseEntity<>(ResultResponse.failed(e.getProductCodeEnum(), e.getMessage()), e.getHttpStatus());
    }
}
