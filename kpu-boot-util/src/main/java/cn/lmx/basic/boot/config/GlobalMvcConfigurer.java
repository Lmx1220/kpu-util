package cn.lmx.basic.boot.config;

import cn.lmx.basic.boot.interceptor.HeaderThreadLocalInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lmx
 * @description: 公共配置类, 一些公共工具配置
 * @date 2023/6/16 22:05
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
