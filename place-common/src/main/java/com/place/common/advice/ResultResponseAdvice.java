package com.place.common.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.place.common.domain.response.ResultResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
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
        if (request.getURI().getPath().startsWith("/swagger-resources") ||
                request.getURI().getPath().startsWith("/v2/api-docs")) {
            return body;
        }
        if (body == null || body instanceof ResultResponse) {
            return body;
        }
        final ResultResponse<Object> result = ResultResponse.ok(body);
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("将 Response 对象序列化为 json 字符串时发生异常", e);
            }
        }
        return result;
    }
}
