package cn.lmx.basic.jwt;

import cn.lmx.basic.constant.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lmx
 * @version 1.0
 * @description: 认证服务端 属性
 * @date 2023/7/4 14:27
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = Constants.PROJECT_PREFIX + ".authentication";

    /**
     * 过期时间 2h
     * 单位：s
     */
    private Long expire = 7200L;
    /**
     * 刷新token的过期时间 8h
     * 单位：s
     */
    private Long refreshExpire = 28800L;
    /**
     * 设置解析token时，允许的误差
     * 单位：s
     * 使用场景1：多台服务器集群部署时，服务器时间戳可能不一致
     * 使用场景2：？
     */
    private Long allowedClockSkewSeconds = 60L;

}
