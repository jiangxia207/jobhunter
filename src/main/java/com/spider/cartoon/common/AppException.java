package com.spider.cartoon.common;

public class AppException extends RuntimeException {
    private Code code;
    public AppException(Code code) {
        super(code.getText());
        this.code = code;
    }

    public AppException(String arg0) {
        super(arg0);
        this.code = Code.FAIL;
    }

    public AppException(Code code, String arg0) {
        super(arg0);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8883557640278638967L;

}
