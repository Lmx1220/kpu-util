package cn.lmx.basic.mq.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import cn.lmx.basic.constant.Constants;


/**
 * 操作日志配置类
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = MqProperties.PREFIX)
public class MqProperties {
    public static final String PREFIX = Constants.PROJECT_PREFIX + ".rabbitmq";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

}
