package cn.lmx.basic.log;


import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import cn.lmx.basic.jackson.JsonUtil;
import cn.lmx.basic.log.aspect.SysLogAspect;
import cn.lmx.basic.log.event.SysLogListener;
import cn.lmx.basic.log.monitor.PointUtil;
import cn.lmx.basic.log.properties.OptLogProperties;

/**
 * 日志自动配置
 * <p>
 * 启动条件：
 * 1，存在web环境
 * 2，配置文件中kpu.log.enabled=true 或者 配置文件中不存在：kpu.log.enabled 值
 *
 * @author lmx
 * @date 2024/02/07
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@EnableConfigurationProperties(OptLogProperties.class)
@ConditionalOnProperty(prefix = OptLogProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = OptLogProperties.PREFIX, name = "type", havingValue = "LOGGER", matchIfMissing = true)
    public SysLogListener sysLogListener() {
        return new SysLogListener(log -> PointUtil.debug("0", "OPT_LOG", JsonUtil.toJson(log)));
    }
}
