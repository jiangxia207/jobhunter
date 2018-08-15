package com.spider.cartoon.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "episode_image")
public class EpisodeImage {
    
    private String id;
    private String cartoonId;
    private String episodeId;
    private Integer code ;
    private String img;
    
    
    public String getCartoonId() {
        return cartoonId;
    }
    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }
    public String getEpisodeId() {
        return episodeId;
    }
    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
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
    
    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    
}
