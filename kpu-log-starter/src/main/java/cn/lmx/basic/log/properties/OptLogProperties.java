package cn.lmx.basic.log.properties;

import cn.lmx.basic.constant.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lmx
 * @version 1.0
 * @description: 操作日志配置类
 * @date 2023/7/4 14:27
 */
@ConfigurationProperties(prefix = OptLogProperties.PREFIX)
@Data
@NoArgsConstructor
public class OptLogProperties {
    public static final String PREFIX = Constants.PROJECT_PREFIX + ".log";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * 日志存储类型
     */
    private OptLogType type = OptLogType.DB;
}
