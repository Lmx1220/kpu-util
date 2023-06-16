package cn.lmx.basic.exception;


import cn.lmx.basic.exception.code.ExceptionCode;

/**
 * @author lmx
 * @version 1.0
 * @description: 401 未认证 未登录
 * @date 2023/6/16 12:53
 */
public class UnauthorizedException extends BaseUncheckedException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(int code, String message) {
        super(code, message);
    }

    public UnauthorizedException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public static UnauthorizedException wrap(String msg) {
        return new UnauthorizedException(ExceptionCode.UNAUTHORIZED.getCode(), msg);
    }

    @Override
    public String toString() {
        return "UnauthorizedException [message=" + getMessage() + ", code=" + getCode() + "]";
    }

}
