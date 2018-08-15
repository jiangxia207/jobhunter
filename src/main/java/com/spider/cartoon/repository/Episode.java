package com.spider.cartoon.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spider.cartoon.common.CommonConstants;
import com.spider.cartoon.common.CommonConstants.FeeType;

@Entity
@Table(name = "episode")
public class Episode {
    
    private String id;
    private String cartoonId;
    private String name;
    private FeeType feeType = FeeType.free;
    private Integer click = CommonConstants.zero;
    private Date updateTime;
    private Integer cost = CommonConstants.zero;
    private Integer code ;
    private String img;
    
    public enum SourceType {
        mkz("漫客栈"),
        mg("玫瑰");
        
        SourceType(String type) {
            this.type = type;
        }

        private String type;

        public String getType(){
            return type;
        }
    }
    
    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String getId() {
        return id;
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
    
    @Enumerated(EnumType.STRING)
    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    
}
