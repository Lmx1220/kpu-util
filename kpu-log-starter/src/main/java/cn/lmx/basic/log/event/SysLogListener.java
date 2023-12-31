package cn.lmx.basic.log.event;


import cn.lmx.basic.context.ContextUtil;
import cn.lmx.basic.model.log.OptLogDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;


/**
 * @author lmx
 * @version 1.0
 * @description: 异步监听日志事件
 * @date 2023/7/4 14:27
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final Consumer<OptLogDTO> consumer;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        OptLogDTO sysLog = (OptLogDTO) event.getSource();

        // 非租户模式 (NONE) ， 需要修改这里的判断
        if (sysLog == null) {
            log.warn("租户编码不存在，忽略操作日志=={}", sysLog != null ? sysLog.getRequestUri() : "");
            return;
        }
        ContextUtil.setTenant(sysLog.getTenantCode());

        consumer.accept(sysLog);
    }

}
