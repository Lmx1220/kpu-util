package cn.lmx.basic.log.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import cn.lmx.basic.context.ContextUtil;
import cn.lmx.basic.model.log.OptLogDTO;

import java.util.function.Consumer;


/**
 * 异步监听日志事件
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
        ContextUtil.setTenantId(sysLog.getTenantId());
        if (sysLog.getBasePoolNameHeader() != null) {
            ContextUtil.setTenantBasePoolName(sysLog.getBasePoolNameHeader());
        }
        if (sysLog.getExtendPoolNameHeader() != null) {
            ContextUtil.setTenantExtendPoolName(sysLog.getExtendPoolNameHeader());
        }
        consumer.accept(sysLog);
    }

}
