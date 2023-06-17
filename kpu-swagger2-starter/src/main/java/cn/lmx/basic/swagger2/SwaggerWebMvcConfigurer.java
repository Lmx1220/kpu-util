package cn.lmx.basic.swagger2;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lmx
 * @description: Swagger 配置
 * @date 2023/6/17 13:24
 */
public class SwaggerWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars" +
                        "*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
