package cn.lmx.basic.exception.code;

/**
 * 异常编码
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
