package cn.lmx.basic.jwt;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import cn.lmx.basic.jwt.properties.JwtProperties;

/**
 * 认证服务端配置
 *
 * @author lmx
 * @date 2024/02/07
 */
@EnableConfigurationProperties(value = {
        JwtProperties.class,
})
public class JwtConfiguration {

    @Bean
    public TokenHelper getTokenUtil(JwtProperties authServerProperties) {
        return new TokenHelper(authServerProperties);
    }
}
