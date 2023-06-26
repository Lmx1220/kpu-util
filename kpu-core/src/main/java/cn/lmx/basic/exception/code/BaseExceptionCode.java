package cn.lmx.basic.exception.code;

/**
 * @author lmx
 * @version 1.0
 * @description: 异常编码
 * @date 2023/7/4 14:27
 */
public interface BaseExceptionCode {
    /**
     * 异常编码
     *
     * @return 异常编码
     */
    int getCode();

    /**
     * 异常消息
     *
     * @return 异常消息
     */
    String getMsg();
}
