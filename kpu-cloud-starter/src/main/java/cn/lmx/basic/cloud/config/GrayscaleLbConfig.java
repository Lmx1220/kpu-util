package cn.lmx.basic.cloud.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import cn.lmx.basic.cloud.rule.GrayscaleVersionRoundRobinLoadBalancer;
import cn.lmx.basic.utils.StrPool;

/**
 * 灰度配置
 *
  * @author lmx
 * @version v1.0
 * @date 2024/02/07  02:04 上午
 * @create [2024/02/07  02:04 上午 ] [lmx] [初始创建]
 */
public class GrayscaleLbConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
                                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME, StrPool.EMPTY);
        return new GrayscaleVersionRoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}
