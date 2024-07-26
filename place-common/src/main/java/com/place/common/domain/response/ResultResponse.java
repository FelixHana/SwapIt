package com.place.common.domain.response;

import cn.hutool.core.util.StrUtil;
import com.place.common.domain.enums.BaseCodeEnum;
import lombok.AllArgsConstructor;
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

    private String code;
    private String msg;
    private final Date timestamp = new Date();
    private T data; //返回的数据

    public static <T> ResultResponse<T> ok() {
        return restResult(HttpStatus.OK.name(), null, null);
    }
    public static <T> ResultResponse<T> ok(T data) {
        return restResult(HttpStatus.OK.name(), null, data);
    }
    public static <T> ResultResponse<T> ok(String msg, T data) {
        return restResult(HttpStatus.OK.name(), msg, data);
    }

    public static <T> ResultResponse<T> failed(HttpStatus httpStatus) {
        return restResult(httpStatus.name(), null, null);
    }
    public static <T> ResultResponse<T> failed(HttpStatus httpStatus, String msg) {
        return restResult(httpStatus.name(), msg, null);
    }
    public static <T> ResultResponse<T> failed(HttpStatus httpStatus, T data) {
            return restResult(httpStatus.name(), null, data);
        }

    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum) {
        return restResult(codeEnum.getEnumName(), codeEnum.getMessage(), null);
    }
    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum, String msg) {
        return restResult(codeEnum.getEnumName(),codeEnum.getMessage() + (StrUtil.isNotBlank(msg) ? " [detail: " + msg + "]." : ""), null);
    }
    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum, T data) {
        return restResult(codeEnum.getEnumName(), codeEnum.getMessage(), data);
    }
    public static <T> ResultResponse<T> failed(BaseCodeEnum codeEnum, String msg, T data) {
        return restResult(codeEnum.getEnumName(),codeEnum.getMessage() + (StrUtil.isNotBlank(msg) ? " [detail: " + msg + "]." : ""), data);
    }

    public static <T> ResultResponse<T> restResult(String code, String msg, T data) {
        ResultResponse<T> apiResult = new ResultResponse<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }

}
