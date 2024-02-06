package cn.lmx.basic.swagger2;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger 配置
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
