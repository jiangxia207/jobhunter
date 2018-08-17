package com.spider.cartoon.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spider.cartoon.common.CrawlPageProcess;
import com.spider.cartoon.common.Res;
import com.spider.cartoon.job.SpiderTask;
import com.spider.cartoon.repository.Cartoon;
import com.spider.cartoon.service.CartoonService;

import io.swagger.annotations.ApiOperation;


@RestController
public class CartoonController {
    
    @Autowired
    private CartoonService cartoonService;
    
    @Autowired
    @Qualifier("meiguiSpider")
    private CrawlPageProcess meiguiSpider;
    
    @Autowired
    @Qualifier("mkzhanSpider")
    private CrawlPageProcess mkzhanSpider;
    
    
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    @ApiOperation(value = "玫瑰")
    @GetMapping(value = "/meigui/crawl/all")
    public Res<Cartoon> crawlAll(HttpServletRequest request) {
        
        executorService.submit(new SpiderTask(meiguiSpider));
        return Res.success();
    }
    
    
    @ApiOperation(value = "mkzhan")
    @GetMapping(value = "/mkzhan/crawl/all")
    public Res<Cartoon> save(HttpServletRequest request) {
        
        executorService.submit(new SpiderTask(mkzhanSpider));
        return Res.success();
    }

}
