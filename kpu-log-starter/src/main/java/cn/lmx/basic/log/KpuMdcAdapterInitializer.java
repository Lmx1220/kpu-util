package cn.lmx.basic.log;

import org.slf4j.KpuMdcAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

/**
 * @author lmx
 * @version 1.0
 * @description: 初始化TtlMDCAdapter实例，并替换MDC中的adapter对象
 * @date 2023/6/16 23:52
 */
public class KpuMdcAdapterInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        //加载TtlMDCAdapter实例
        KpuMdcAdapter.getInstance();
    }
}
