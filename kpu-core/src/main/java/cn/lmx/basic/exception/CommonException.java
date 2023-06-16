package cn.lmx.basic.exception;

/**
 * @author lmx
 * @version 1.0
 * @description: 非业务异常
 * <p>
 * 用于在处理非业务逻辑时，进行抛出的异常。
 * @date 2023/6/16 12:51
 * @see Exception
 */
public class CommonException extends BaseCheckedException {


    public CommonException(int code, String message) {
        super(code, message);
    }

    public CommonException(int code, String format, Object... args) {
        super(code, format, args);
    }

    public CommonException wrap(int code, String format, Object... args) {
        return new CommonException(code, format, args);
    }

    @Override
    public String toString() {
        return "BizException [message=" + getMessage() + ", code=" + getCode() + "]";
    }
}
