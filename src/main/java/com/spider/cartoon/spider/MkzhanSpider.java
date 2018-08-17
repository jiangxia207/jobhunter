package com.spider.cartoon.spider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.spider.cartoon.common.CommonConstants.SourceType;
import com.spider.cartoon.common.CrawlPageProcess;
import com.spider.cartoon.common.DatetimeUtilies;
import com.spider.cartoon.common.ID;
import com.spider.cartoon.common.StringUtilies;
import com.spider.cartoon.repository.Cartoon;
import com.spider.cartoon.repository.Cartoon.ProgressType;
import com.spider.cartoon.repository.Episode;
import com.spider.cartoon.repository.EpisodeImage;
import com.spider.cartoon.service.CartoonService;
import com.spider.cartoon.service.EpisodeImageService;
import com.spider.cartoon.service.EpisodeService;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

@Component
public class MkzhanSpider implements CrawlPageProcess{

    public static final Logger logger = LoggerFactory.getLogger(MkzhanSpider.class);
    
    @Autowired
    private CartoonService cartoonService;
    @Autowired
    private EpisodeImageService episodeImageService;
    @Autowired
    private EpisodeService episodeService;
    
    
    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000).setTimeOut(10000).setRetryTimes(0)
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");


    private static final String detailPrefix = "https://www.mkzhan.com";
    
    
    
    public static List<Episode> extractEpisode(Html html, String id)
    {
        List<Episode> result = new ArrayList<Episode>();
        List<String> chapterNames = html.xpath("//a[@class='j-chapter-item']/text()").all();
        List<String> chapterIds = html.xpath("//a[@class='j-chapter-item']/@data-chapterid").all();
        Collections.reverse(chapterNames);
        Collections.reverse(chapterIds);
        
        int index = 0;
        for(String chapterIdInfo:chapterIds)
        {
            String episodeId = chapterIdInfo;
            Episode episode = new Episode();
            episode.setId(episodeId);
            episode.setCartoonId(id);
            episode.setName(chapterNames.get(index).trim());
            episode.setCode(index++);
            result.add(episode);
        }
        return result;
    }
    
    
    public static Cartoon preExtractDetail(String id, String img)
    {
        Cartoon cartoon = new Cartoon();
        cartoon.setId(id);
        cartoon.setImg(img);
        return cartoon;
    }
    
    public static Cartoon extractDetail(Html html, String id) throws ParseException
    {
        String name = html.xpath("//p[contains(@class,'j-comic-title')]/text()").get();
        String introduce = html.xpath("//meta[@name='description']/@content").get();
        String author = html.xpath("//span[@class='name']/a/text()").get();
        List<String> message = html.xpath("//div[@class='de-chapter__title']/span/text()").all();
        
        
        String detailImg = html.xpath("//div[@class='de-info__cover']/img/@data-src").get();
        
        Cartoon cartoon = new Cartoon();
        cartoon.setId(id);
        cartoon.setName(name);
        cartoon.setSourceType(SourceType.mkz);
        cartoon.setProgressType(message.get(0).contains("完结")?ProgressType.end:ProgressType.serialize);
        cartoon.setLatestEpisodeName(message.get(1).substring(11));
        cartoon.setIntroduce(introduce);
        cartoon.setDetailImg(detailImg);
        cartoon.setName(name);
        cartoon.setAuthor(author);
        if(!StringUtilies.isNullOrEmpty(message.get(1).substring(0, 10)))
        {
            cartoon.setUpdateTime(DatetimeUtilies.parse(DatetimeUtilies.DOT_DATE, message.get(1).substring(0, 10)));
        }
        return cartoon;
    }
    
    public void process(Page page) 
    {
        if (page.getUrl().regex("https://www\\.mkzhan\\.com/top/popularity/").match()) 
        {
            List<String> urls = page.getHtml().xpath("//p[@class='comic__title']/a/@href").all();
            Set<String> urlsSet = Sets.newHashSet();
            urlsSet.addAll(urls);
            System.out.println(urlsSet.size());
            
            for(String url:urlsSet)
            {
                if(StringUtilies.isNullOrEmpty(url))
                {
                    continue;
                }
                Request req = new Request();
                req.setUrl(detailPrefix+url);
                req.setMethod(HttpConstant.Method.GET);
                page.addTargetRequest(req);
            }
        }
        else if (page.getUrl().regex("https://www\\.mkzhan\\.com/[0-9a-z]{2,10}/$").match()) 
        {
            //详情页
            Selectable aax = page.getUrl().replace("https://www\\.mkzhan\\.com/", "");
            String id = aax.all().get(0).split("/")[0];
            
            List<String> episodeUrls = page.getHtml().xpath("//a[@class='j-chapter-item']/@href").all();
            Cartoon cartoon = null;
            try
            {
                cartoon = extractDetail(page.getHtml(), id);
                //TODO 先不保存
                Cartoon cartoonDB = cartoonService.saveIfNotExist(cartoon);
                if(cartoonDB == null)
                {
                    //数据库已经存在，直接返回
                    return ;
                }
                
                List<Episode> episodes = extractEpisode(page.getHtml(), id);
                episodeService.save(episodes);
                
                for(String url:episodeUrls)
                {
                    if(StringUtilies.isNullOrEmpty(url))
                    {
                        continue;
                    }
                    Request req = new Request();
                    req.setUrl(detailPrefix+url);
                    req.setMethod(HttpConstant.Method.GET);
                    page.addTargetRequest(req);
                }
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
                logger.warn("crawl failed:{},{}",id, e.getMessage());
                return;
            }
        }
        else if (page.getUrl().regex("https://www\\.mkzhan\\.com/[0-9a-z]{2,10}/[0-9a-z]{2,10}\\.html").match()) 
        {
            String url = page.getUrl().replace("https://www.mkzhan.com/", "").get();
            String id = url.split("/")[0];
            String episodeId = url.split("/")[1].split("\\.")[0];
            
            List<String> imgs = page.getHtml().xpath("//div[contains(@class,'rd-article__pic')]/img/@src").all();
            if(imgs.get(0).startsWith("//static"))
            {
                imgs = page.getHtml().xpath("//div[contains(@class,'rd-article__pic')]/img/@data-src").all();
            }
            int code = 0;
            List<EpisodeImage> episodeImages = new ArrayList<>();
            for(String img:imgs)
            {
                EpisodeImage episodeImage = new EpisodeImage();
                episodeImage.setId(ID.uuid());
                episodeImage.setCartoonId(id);
                episodeImage.setEpisodeId(episodeId);
                episodeImage.setCode(code);
                episodeImage.setImg(img);
                episodeImages.add(episodeImage);
                
                code++;
                
                if(StringUtilies.isNullOrEmpty(episodeImage.getImg()) || episodeImage.getImg().startsWith("//static"))
                {
                    System.out.println();
                }
            }
            episodeImageService.save(episodeImages);
        }
    }
    
    public Site getSite() {
        return site;
    }

    public void crawl() 
    {
        Request req = new Request();
        req.setMethod(HttpConstant.Method.GET);
        req.setUrl("https://www.mkzhan.com/top/popularity/");
        
        
        Spider.create(this).addRequest(req).thread(1).run();
    }
    
}
