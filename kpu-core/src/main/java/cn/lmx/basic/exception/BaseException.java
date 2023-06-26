package cn.lmx.basic.exception;

/**
 * @author lmx
 * @version 1.0
 * @description: 异常接口类
 * @date 2023/7/4 14:27
 */
public interface BaseException {

    /**
     * 统一参数验证异常码
     */
    int BASE_VALID_PARAM = -9;

    /**
     * 返回异常信息
     *
     * @return 异常信息
     */
    String getMessage();

    /**
     * 返回异常编码
     *
     * @return 异常编码
     */
    int getCode();

}
