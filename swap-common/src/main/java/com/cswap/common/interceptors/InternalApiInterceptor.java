package com.cswap.common.interceptors;

import com.cswap.common.annotation.InternalApi;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZCY-
 */
public class InternalApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws CommonException {
        // skip when handler is ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            InternalApi methodAnnotation = handlerMethod.getMethodAnnotation(InternalApi.class);
            if (methodAnnotation == null) {
                InternalApi annotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(InternalApi.class);
                if (annotation == null) {
                    return true;
                }
            }
            if ("external".equals(request.getHeader("from-gateway"))) {
                throw new CommonException(CommonCodeEnum.INTERNAL_API);
            }
        }
        return true;
    }
}
