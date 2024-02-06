package cn.lmx.basic.cloud.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import cn.lmx.basic.constant.Constants;

/**
 * 灰度配置
 * 默认开启，视情况关闭。
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
@ConditionalOnProperty(value = Constants.PROJECT_PREFIX + ".grayscale.enabled", havingValue = "true", matchIfMissing = true)
@LoadBalancerClients(defaultConfiguration = GrayscaleLbConfig.class)
public class GrayscaleConfig {

}
