package com.cswap.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException implements ApiException{

    public HttpException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = null;
    }
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return httpStatus.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
