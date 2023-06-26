package cn.lmx.basic.exception;


import cn.lmx.basic.exception.code.ExceptionCode;

/**
 * @author lmx
 * @version 1.0
 * @description: 业务参数异常
 * <p>
 * 用于在业务中，检测到非法参数时，进行抛出的异常。
 * @date 2023/7/4 14:27
 */
public class ArgumentException extends BaseUncheckedException {

    private static final long serialVersionUID = -3843907364558373817L;

    public ArgumentException(Throwable cause) {
        super(cause);
    }

    public ArgumentException(String message) {
        super(ExceptionCode.BASE_VALID_PARAM.getCode(), message);
    }

    public ArgumentException(String message, Throwable cause) {
        super(ExceptionCode.BASE_VALID_PARAM.getCode(), message, cause);
    }

    public ArgumentException(final String format, Object... args) {
        super(ExceptionCode.BASE_VALID_PARAM.getCode(), format, args);
    }

    @Override
    public String toString() {
        return "ArgumentException [message=" + getMessage() + ", code=" + getCode() + "]";
    }

}
