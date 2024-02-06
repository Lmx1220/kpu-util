package cn.lmx.basic.boot.config;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import cn.lmx.basic.boot.interceptor.HeaderThreadLocalInterceptor;

/**
 * 公共配置类, 一些公共工具配置
 *
 * @author lmx
 * @date 2024/02/07
 */
@AllArgsConstructor
public class GlobalMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderThreadLocalInterceptor())
                .addPathPatterns("/**")
                .order(-20);
    }
}
