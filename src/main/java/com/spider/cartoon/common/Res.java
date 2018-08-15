package com.spider.cartoon.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.ApiModelProperty;

public class Res<T> {
    private static final Logger logger = LoggerFactory.getLogger(Res.class);

    @ApiModelProperty(required = true)
    private Code code = Code.SUCCESS;
    @ApiModelProperty(value = "描述")
    private String message = "操作成功";
    @ApiModelProperty("具体数据")
    private T data;

    public Res() {

    }

    public Res(Exception e) {
        if (e instanceof AppException) {
            AppException ae = (AppException) e;
            setMessage(ae.getMessage());
            setCode(ae.getCode());
        } else if (e instanceof ValidateException) {
            ValidateException ae = (ValidateException) e;
            setMessage(ae.getMessage());
            setCode(null == ae.getCode() ? Code.FAIL : ae.getCode());
        } else {
            logger.error(e.getMessage(), e);
            setMessage(e.getMessage());
            setCode(Code.FAIL);
        }
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getMessage() {
        if (null == message) {
            if (null != code) {
                this.message = code.getText();
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public Res<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> Res<T> success() {
        return success(null);
    }

    public static <T> Res<T> success(T data) {
        Res<T> res = new Res<>();
        res.setCode(Code.SUCCESS);
        res.setMessage(Code.SUCCESS.getText());
        res.setData(data);
        return res;
    }

    public static <T> Res<T> fail(Code code) {
        Res<T> res = new Res<>();
        res.setCode(Code.FAIL);
        res.setMessage(code.getText());
        return res;
    }

    public static <T> Res<T> fail() {
        return fail(null);
    }

}
