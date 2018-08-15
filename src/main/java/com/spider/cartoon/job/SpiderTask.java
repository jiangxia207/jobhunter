package com.spider.cartoon.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spider.cartoon.common.CrawlPageProcess;

public class SpiderTask extends Thread {
    public static final Logger logger = LoggerFactory.getLogger(SpiderTask.class);
    
    private CrawlPageProcess meiguiSpider;
    
    public SpiderTask(CrawlPageProcess meiguiSpider) {
        this.meiguiSpider = meiguiSpider;
    }

    @Override
    public void run()
    {
        logger.warn("SpiderTask start");
        meiguiSpider.crawl();
        logger.warn("SpiderTask end");
    }

}
