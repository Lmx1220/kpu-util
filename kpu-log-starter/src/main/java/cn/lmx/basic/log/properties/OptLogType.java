package cn.lmx.basic.log.properties;

/**
 * 日志类型
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
}
