package com.cswap.common.domain.response;

import cn.hutool.core.util.StrUtil;
import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZCY-
 */
@Data
@NoArgsConstructor
public class ResultResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Date timestamp = new Date();
    private String code;
    private String msg;
    private T data; //返回的数据

    public static <T> ResultResponse<T> success() {
        return restResult(HttpStatus.OK.name(), "success", null);
    }

    public static <T> ResultResponse<T> success(T data) {
        return restResult(HttpStatus.OK.name(), "success", data);
    }

    public static <T> ResultResponse<T> success(String msg, T data) {
        return restResult(HttpStatus.OK.name(), msg, data);
    }

    public static <T> ResultResponse<T> failed(HttpStatus httpStatus) {
        return restResult(httpStatus.name(), "error", null);
    }

    public static <T> ResultResponse<T> failed(HttpStatus httpStatus, String msg) {
        return restResult(httpStatus.name(), msg, null);
    }

    public static <T> ResultResponse<T> failed(HttpStatus httpStatus, T data) {
        return restResult(httpStatus.name(), "error", data);
    }

    public static <T> ResultResponse<T> failed(HttpStatus httpStatus, String msg, T data) {
        return restResult(httpStatus.name(), msg, data);
    }

    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum) {
        return restResult(codeEnum.getId().toString(), codeEnum.getEnumName() + ": " + codeEnum.getMessage(), null);
    }

    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum, String msg) {
        return restResult(codeEnum.getId().toString(), codeEnum.getEnumName() + ": " + codeEnum.getMessage() + (StrUtil.isNotBlank(msg) ? " [detail: " + msg + "]." : ""), null);
    }

    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum, T data) {
        return restResult(codeEnum.getId().toString(), codeEnum.getEnumName() + ": " + codeEnum.getMessage(), data);
    }

    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum, String msg, T data) {
        return restResult(codeEnum.getId().toString(), codeEnum.getEnumName() + ": " + codeEnum.getMessage() + (StrUtil.isNotBlank(msg) ? " [detail: " + msg + "]." : ""), data);
    }

    public static <T> ResultResponse<T> restResult(String code, String msg, T data) {
        ResultResponse<T> apiResult = new ResultResponse<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }

}
