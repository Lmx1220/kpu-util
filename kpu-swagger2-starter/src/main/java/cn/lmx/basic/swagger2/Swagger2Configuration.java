package cn.lmx.basic.swagger2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import static cn.lmx.basic.swagger2.Swagger2Configuration.TRUE;

/**
 * Swagger2 启动类. 参考： Knife4jAutoConfiguration
 * 启动条件：
 * 1，配置文件中： ${SwaggerProperties.PREFIX}.enable=true
 * 2，配置文件中不存在： ${SwaggerProperties.PREFIX}.enable 值
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
@ConditionalOnProperty(prefix = "knife4j", name = "enable", havingValue = TRUE, matchIfMissing = true)
public class Swagger2Configuration {

    public static final String TRUE = "true";

    @Bean
    @ConditionalOnClass(SwaggerWebMvcConfigurer.class)
    public SwaggerWebMvcConfigurer getSwaggerWebMvcConfigurer() {
        return new SwaggerWebMvcConfigurer();
    }

}
