package cn.lmx.basic.exception;

/**
 * @author lmx
 * @version 1.0
 * @description: 运行期异常基类
 * @date 2023/6/16 12:49
 */
public abstract class BaseCheckedException extends Exception implements BaseException {

    private static final long serialVersionUID = 2706069899924648586L;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 具体异常码
     */
    private int code;

    public BaseCheckedException(final int code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseCheckedException(final int code, final String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }

    /**
     * 获取 异常消息
     *
     * @return 异常消息
     */
    @Override
    public String getMessage() {
        return message;
    }


    /**
     * 获取 错误码
     *
     * @return 错误码
     */
    @Override
    public int getCode() {
        return code;
    }


}
