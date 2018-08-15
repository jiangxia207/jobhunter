package com.spider.cartoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spider.cartoon.dao.EpisodeImageDao;
import com.spider.cartoon.repository.EpisodeImage;

@Service
public class EpisodeImageService {

    @Autowired
    private EpisodeImageDao episodeImageDao;
    
    
    @Transactional
    public void save(Iterable<EpisodeImage> episodes)
    {
        episodeImageDao.save(episodes);
    }
    
        
    
}
