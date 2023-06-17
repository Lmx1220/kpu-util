package cn.lmx.basic.log.event;


import cn.lmx.basic.model.log.OptLogDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author lmx
 * @version 1.0
 * @description: 系统日志事件
 * @date 2023/6/16 23:54
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(OptLogDTO source) {
        super(source);
    }
}
