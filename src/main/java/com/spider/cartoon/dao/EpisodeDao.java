package com.spider.cartoon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.spider.cartoon.repository.Episode;

@Component
public interface EpisodeDao  extends JpaRepository<Episode, String>{

}
