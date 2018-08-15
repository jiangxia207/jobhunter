package com.spider.cartoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spider.cartoon.dao.EpisodeDao;
import com.spider.cartoon.repository.Episode;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeDao episodeDao;
    
    
    @Transactional
    public void save(Iterable<Episode> episodes)
    {
        episodeDao.save(episodes);
    }
    
        
    
}
