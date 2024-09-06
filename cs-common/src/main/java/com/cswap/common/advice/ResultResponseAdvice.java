package com.cswap.common.advice;

import com.cswap.common.domain.response.ResultResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getGenericParameterType().equals(ResultResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (request.getURI().getPath().startsWith("/v3/api-docs")) {
            return body;
        }
        if (body == null || body instanceof ResultResponse) {
            return body;
        }

        // 内部请求
        if (!request.getHeaders().containsKey("from-gateway")) {
            return body;
        }
        final ResultResponse<Object> result = ResultResponse.ok(body);
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            // 转换 JSON 时配置时间戳格式，否则变 long
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
            // 设置 media type 否则以 text 类型响应
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            try {
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("将 Response 对象序列化为 json 字符串时发生异常", e);
            }
        }
        return result;
    }
}
