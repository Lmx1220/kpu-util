package cn.lmx.basic.log.properties;

/**
 * @author lmx
 * @version 1.0
 * @description: 日志类型
 * @date 2023/6/16 23:53
 */
public enum OptLogType {
    /**
     * 通过logger记录日志到本地
     */
    LOGGER,
    /**
     * 记录日志到数据库
     */
    DB,
    ;
}
