package cn.lmx.basic.log;


import cn.lmx.basic.constant.Constants;
import cn.lmx.basic.jackson.JsonUtil;
import cn.lmx.basic.log.aspect.SysLogAspect;
import cn.lmx.basic.log.event.SysLogListener;
import cn.lmx.basic.log.monitor.PointUtil;
import cn.lmx.basic.log.properties.OptLogProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lmx
 * @version 1.0
 * @description: 日志自动配置
 * <p>
 * 启动条件：
 * 1，存在web环境
 * 2，配置文件中kpu.log.enabled=true 或者 配置文件中不存在：kpu.log.enabled 值
 * @date 2023/7/4 14:27
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = OptLogProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnExpression("${" + Constants.PROJECT_PREFIX + ".log.enabled:true} && 'LOGGER'.equals('${" + Constants.PROJECT_PREFIX + ".log.type:LOGGER}')")
    public SysLogListener sysLogListener() {
        return new SysLogListener(log -> PointUtil.debug("0", "OPT_LOG", JsonUtil.toJson(log)));
    }
}
