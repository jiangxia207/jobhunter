package com.spider.cartoon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.spider.cartoon.repository.EpisodeImage;

@Component
public interface EpisodeImageDao  extends JpaRepository<EpisodeImage, String>{

}
