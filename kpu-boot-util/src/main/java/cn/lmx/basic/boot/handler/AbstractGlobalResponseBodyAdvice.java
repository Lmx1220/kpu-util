package cn.lmx.basic.boot.handler;

import cn.lmx.basic.annotation.base.IgnoreResponseBodyAdvice;
import cn.lmx.basic.base.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author lmx
 * @description: 全局响应体包装
 * @date 2023/7/4 14:27
 */
public class AbstractGlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 类上如果被 IgnoreResponseBodyAdvice 标识就不拦截
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseBodyAdvice.class)) {
            return false;
        }

        // 方法上被标注也不拦截
        return !Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseBodyAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return null;
        }
        if (o instanceof R) {
            return o;
        }
        return R.success(o);
    }
}
