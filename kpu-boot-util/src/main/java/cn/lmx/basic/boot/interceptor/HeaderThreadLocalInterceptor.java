package cn.lmx.basic.boot.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.context.ContextConstants;
import cn.lmx.basic.context.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.lmx.basic.boot.utils.WebUtils.getHeader;


/**
 * @author lmx
 * @description: 拦截器：
 * 将请求头数据，封装到BaseContextHandler(ThreadLocal)
 * <p>
 * 该拦截器要优先于系统中其他的业务拦截器
 * <p>
 * @date 2023/7/4 14:27
 */
@Slf4j
public class HeaderThreadLocalInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!ContextUtil.getBoot()) {
            ContextUtil.setPath(getHeader(request, ContextConstants.PATH_HEADER));
            ContextUtil.setUserId(getHeader(request, ContextConstants.JWT_KEY_USER_ID));
            ContextUtil.setAccount(getHeader(request, ContextConstants.JWT_KEY_ACCOUNT));
            ContextUtil.setName(getHeader(request, ContextConstants.JWT_KEY_NAME));
            ContextUtil.setTenant(getHeader(request, ContextConstants.JWT_KEY_TENANT));
            ContextUtil.setSubTenant(getHeader(request, ContextConstants.JWT_KEY_SUB_TENANT));

            String traceId = request.getHeader(ContextConstants.TRACE_ID_HEADER);
            MDC.put(ContextConstants.LOG_TRACE_ID, StrUtil.isEmpty(traceId) ? StrUtil.EMPTY : traceId);
            MDC.put(ContextConstants.JWT_KEY_TENANT, getHeader(request, ContextConstants.JWT_KEY_TENANT));
            MDC.put(ContextConstants.JWT_KEY_SUB_TENANT, getHeader(request, ContextConstants.JWT_KEY_SUB_TENANT));
            MDC.put(ContextConstants.JWT_KEY_USER_ID, getHeader(request, ContextConstants.JWT_KEY_USER_ID));
        }
        // cloud
        ContextUtil.setGrayVersion(getHeader(request, ContextConstants.GRAY_VERSION));
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContextUtil.remove();
    }
}
