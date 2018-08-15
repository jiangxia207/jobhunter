package com.spider.cartoon.model;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:28
 */
public class MeiguiChapter  {
    
    private String id;
    private String cartoonId;
    private String name;
    private Integer code;
    private Integer imageNo;
    public String getId() {
        return id;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCartoonId() {
        return cartoonId;
    }
    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }
    public Integer getImageNo() {
        return imageNo;
    }
    public void setImageNo(Integer imageNo) {
        this.imageNo = imageNo;
    }
    
}
