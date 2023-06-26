package cn.lmx.basic.validator.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author lmx
 * @description: 验证请求包装器
 * @date 2023/7/4 14:27
 */
@SuppressWarnings("ALL")
public class HttpServletRequestValidatorWrapper extends HttpServletRequestWrapper {

    private String formPath;

    public HttpServletRequestValidatorWrapper(HttpServletRequest request, String uri) {
        super(request);
        this.formPath = uri;
    }

    @Override
    public String getRequestURI() {
        return this.formPath;
    }

    @Override
    public String getServletPath() {
        return this.formPath;
    }
}
