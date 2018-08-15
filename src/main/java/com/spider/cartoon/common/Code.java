
package com.spider.cartoon.common;

public enum Code {
    SUCCESS("成功"),
    FAIL("失败"),
    CARTOON_EXIST("已存在");

    private Code(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

}
