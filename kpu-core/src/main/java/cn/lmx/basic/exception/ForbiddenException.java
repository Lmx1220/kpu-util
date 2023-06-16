package cn.lmx.basic.exception;

import cn.lmx.basic.exception.code.BaseExceptionCode;
import cn.lmx.basic.exception.code.ExceptionCode;

/**
 * @author lmx
 * @version 1.0
 * @description: 403  禁止访问
 * @date 2023/6/16 12:52
 */
public class ForbiddenException extends BaseUncheckedException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException(int code, String message) {
        super(code, message);
    }

    public static ForbiddenException wrap(BaseExceptionCode ex) {
        return new ForbiddenException(ex.getCode(), ex.getMsg());
    }

    public static ForbiddenException wrap(String msg) {
        return new ForbiddenException(ExceptionCode.FORBIDDEN.getCode(), msg);
    }

    @Override
    public String toString() {
        return "ForbiddenException [message=" + getMessage() + ", code=" + getCode() + "]";
    }

}
