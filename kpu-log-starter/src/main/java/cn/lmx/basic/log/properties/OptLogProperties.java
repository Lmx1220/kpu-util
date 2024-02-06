package cn.lmx.basic.log.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import cn.lmx.basic.constant.Constants;

import static cn.lmx.basic.log.properties.OptLogProperties.PREFIX;

/**
 * 操作日志配置类
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
@ConfigurationProperties(prefix = PREFIX)
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
