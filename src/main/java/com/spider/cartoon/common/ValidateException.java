package com.spider.cartoon.common;

/**
 * 参数校验异常
 *
 * @author admin
 */
public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 3385381586505888394L;
    private Code code;

    public ValidateException(Code code) {
        this.code = code;
    }

    public ValidateException(String arg0) {
        super(arg0);
    }

    public ValidateException(Code code, String arg0) {
        super(arg0);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

}
