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
import com.spider.cartoon.common.CommonConstants.SourceType;

@Entity
@Table(name = "cartoon")
public class Cartoon {
    
    private String id;
    private String name;
    private String author;
    private String introduce;
    private String latestEpisodeId;
    private String latestEpisodeName;
    private ProgressType progressType;
    private FeeType feeType = FeeType.free;
    private Integer click = CommonConstants.zero;
    private Integer cost = CommonConstants.zero;
    private SourceType SourceType;
    private Date updateTime;
    
    //对于玫瑰来说，列表图是 img， 详情图是 detailImg
    private String img;
    private String detailImg;
    
    
   
    
    public enum ProgressType {
        serialize("连载"),
        end("完结");
        
        ProgressType(String type) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLatestEpisodeId() {
        return latestEpisodeId;
    }

    public void setLatestEpisodeId(String latestEpisodeId) {
        this.latestEpisodeId = latestEpisodeId;
    }

    public String getLatestEpisodeName() {
        return latestEpisodeName;
    }

    public void setLatestEpisodeName(String latestEpisodeName) {
        this.latestEpisodeName = latestEpisodeName;
    }

    
    @Enumerated(EnumType.STRING)
    public ProgressType getProgressType() {
        return progressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
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

    @Enumerated(EnumType.STRING)
    public SourceType getSourceType() {
        return SourceType;
    }

    public void setSourceType(SourceType sourceType) {
        SourceType = sourceType;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    

}
