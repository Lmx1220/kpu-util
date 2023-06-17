package cn.lmx.basic.boot.handler;

import cn.lmx.basic.annotation.base.IgnoreResponseBodyAdvice;
import cn.lmx.basic.base.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author lmx
 * @description: 全局响应体包装
 * @date 2023/6/16 22:05
 */
public class AbstractGlobalResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 类上如果被 IgnoreResponseBodyAdvice 标识就不拦截
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseBodyAdvice.class)) {
            return false;
        }

        // 方法上被标注也不拦截
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseBodyAdvice.class)) {
            return false;
        }
        return true;
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
