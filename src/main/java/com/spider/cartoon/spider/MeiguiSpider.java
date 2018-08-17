package com.spider.cartoon.spider;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

@Component
public class MeiguiSpider implements CrawlPageProcess{

    public static final Logger logger = LoggerFactory.getLogger(MeiguiSpider.class);
    
    @Autowired
    private CartoonService cartoonService;
    @Autowired
    private EpisodeImageService episodeImageService;
    @Autowired
    private EpisodeService episodeService;
    
    
//    private static String wechatUser="Y4%2FKTElnzxTiR6zviWaFM%2Fhd2Yzv0MQVx4DaT%2Fb7l6ni0GRKRZz4t2zwtZ%2BTpuY7iFwLiPnfT%2BQM495A6B0vtq3coOMdGVuen78BHaFeak6zMAZWYR7niegUDdYhEKFysIV0AoayU0KMy7I7tG6dOPhfsxh5KcF8xOj74BQCRTBSGgqq6%2B3mQvGfQL8Cc%2F42mXCqfZGMY%2B%2B9dRUecZVHCbkH%2F%2Fqpjy4SQi5C0QIGoTcyHyuKF3s5s3fz1JELNM927XUz%2BHLfmLUuPQcjNNqjSzrGNTxH1YmMrb5I3K9O%2BJoeTOUoZUJFlSu4UfsIQX4lE9h0SYQvIzq3pFFQKq26uc6Ze0FrqlAcuHACxfp4fIYAWaBxJiJFARvAcgv0TIk842QuVDDUYN0%3D";
//    private static String PHPSESSID = "eb9v0cosalikl9rs22pkuhtob2";
    private static String wechatUser="fpadwgJ0L2On9CTXizR3u35qqjyJKksX%2Fnk2Fs9LCntQPXVja8FSu1IWWD7Tdesk6taEAPjQbVy4cI%2BCue9bBRNkyW7bOYvrLtlffzcpJ2UVgRRJ5VjvPglEe%2F9Xd3YC6z8cvFViwYPIunn82QYbQ5KxRBlQNyk2XtS389F1Lz2EF%2FzzGD4ulfcfjHwbDkgXf9az9HMXKZcMdKPzIUkSbB6IfR4LjxKOC2%2BqtSEpUcNBzH42s%2Bw7fKnFBuel%2FvFMWFhlek4RYk1n9IhBaKqSdB43FFZZkcKGrAmB6C2z62rc4JUaMv%2FftNBxpZEJwoC%2BPVlZNmYwSF7Ggf4pOYS2QazsFRzQzz1%2B%2Byf2tqm1yP0F53bsEWRV637VcKE0V0MynGWhK5ou8hA%3D";
    private static String PHPSESSID = "rt4scg4nedqmd7lb9om75hq686";
    
    
    
    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000).setTimeOut(10000).setRetryTimes(0)
            .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; HUAWEI NXT-TL00 Build/HUAWEINXT-TL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044203 Mobile Safari/537.36 MicroMessenger/6.6.3.1260(0x26060339) NetType/WIFI Language/zh_CN");


    private static final String detailPrefix = "http://x39957.vrpano.cc";
    private static final String chaperPrefix = "http://x39957.vrpano.cc/book/cartoon/read?book_id={0}&chapter_id={1}";
    
    //private static final String detailPattern = "http://x39957.yuntoon.com/book/cartoon/info?book_id=65";
    
    private Map<String, Cartoon> cartoonMap = new HashMap<>();
            
    
    
    public static List<Episode> extractEpisode(Html html, String id)
    {
        List<Episode> result = new ArrayList<Episode>();
        List<String> chapterNames = html.xpath("//ul[@class='book-chapter-list']/li/p/@onclick/text()").all();
        List<String> chapterIds = html.xpath("//ul[@class='book-chapter-list']/li/p/@onclick").all();
        
        int index = 0;
        for(String chapterIdInfo:chapterIds)
        {
            String episodeId = chapterIdInfo.split("'")[1].split("chapter_id=")[1];
            Episode episode = new Episode();
            episode.setId(episodeId);
            episode.setCartoonId(id);
            episode.setName(chapterNames.get(index));
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
    
    public static void extractDetail(Html html, String id, Cartoon cartoon) throws ParseException
    {
        String name = html.xpath("//div[@class='ft-title']/span/text()").get();
        String progress = html.xpath("//div[@class='l-book-count']/span/text()").get();
        String latestEpisode = html.xpath("//div[@class='l-book-count']/text()").get();
        String introduce = html.xpath("//div[@id='m-intro']/p/text()").get();
        String detailImg = html.xpath("//div[@class='hd']/img/@src").get();
        String updateTime = html.regex("\\d{4}\\/\\d{2}\\/\\d{2}").get();
        
        
        cartoon.setId(id);
        cartoon.setName(name);
        cartoon.setSourceType(SourceType.mg);
        cartoon.setProgressType(progress.contains("完结")?ProgressType.end:ProgressType.serialize);
        cartoon.setLatestEpisodeName(StringUtilies.isNullOrEmpty(latestEpisode)?null:latestEpisode.trim());
        cartoon.setIntroduce(introduce);
        cartoon.setDetailImg(detailImg);
        cartoon.setName(name);
        if(!StringUtilies.isNullOrEmpty(updateTime))
        {
            cartoon.setUpdateTime(DatetimeUtilies.parse(DatetimeUtilies.DATE, updateTime));
        }
        
    }
    
    public void process(Page page) 
    {
        if (page.getUrl().regex("http://x39957\\.vrpano\\.cc/category/bookList*").match()) 
        {
            //初始页
            List<String> urls = page.getHtml().xpath("//li[@class='w-shelfBookinfo']/a/@href").all();
            List<String> imgs = page.getHtml().xpath("//div[@class='w-bookPic']/img/@src").all();
            if(CollectionUtils.isEmpty(urls))
            {
                return;
            }
            
            int index = 0;
            for(String url:urls)
            {
                String id = url.replace("/book/cartoon/info?book_id=", "");
                if(cartoonMap.containsKey(id))
                {
                    //如果缓存里面有，忽略掉
                    continue;
                }
                Cartoon cartoon = preExtractDetail(id, imgs.get(index));
                cartoonMap.put(id, cartoon);
                
                
                Request req = new Request();
                req.setUrl(detailPrefix+url);
                req.setMethod(HttpConstant.Method.GET);
                req.addCookie("PHPSESSID", PHPSESSID);
                req.addCookie("x39957_user", wechatUser);
                page.addTargetRequest(req);
                
                index++;
            }
            
            
            //生成分页
            Request req = new Request();
            req.setUrl(page.getUrl().get());
            req.setMethod(HttpConstant.Method.POST);
            req.addCookie("PHPSESSID", PHPSESSID);
            req.addCookie("x39957_user", wechatUser);
            
            String params = new String(page.getRequest().getRequestBody().getBody());
            String[] x = params.split("\\&");
            int pageNo =  Integer.parseInt(x[0].split("=")[1]);
            logger.warn("MeiguiSpider crawl page:{}", pageNo);
            pageNo++;
            
            Map<String, Object> newParams = new HashMap<>();
            newParams.put("p", pageNo);
            newParams.put("limit", 10);
            newParams.put("ul", 1);
            newParams.put("order", "book_view DESC,book_uptime DESC");
            
            req.setRequestBody(HttpRequestBody.form(newParams, "utf-8"));
            
            page.addTargetRequest(req);
            
        }
        else if (page.getUrl().regex("http://x39957\\.vrpano\\.cc/book/cartoon/info\\?book_id=*").match()) 
        {
            Selectable aax = page.getUrl().replace("http://x39957\\.vrpano\\.cc/book/cartoon/info\\?book_id=", "");
            String id = aax.all().get(0);
            
            //此为查看详情
            try
            {
                Cartoon cartoon = cartoonMap.get(id);
                
                extractDetail(page.getHtml(), id, cartoon);
                
                Cartoon cartoonDB = cartoonService.saveIfNotExist(cartoon);
                if(cartoonDB == null)
                {
                    //数据库已经存在，直接返回
                    return ;
                }
                
                List<Episode> episodes = extractEpisode(page.getHtml(), id);
                episodeService.save(episodes);
                for(Episode episode:episodes)
                {
                    Request req = new Request();
                    req.setUrl(MessageFormat.format(chaperPrefix, id, episode.getId()));
                    req.setMethod(HttpConstant.Method.POST);
                    req.addCookie("PHPSESSID", PHPSESSID);
                    req.addCookie("x39957_user", wechatUser);
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
        else if (page.getUrl().regex("http://x39957\\.vrpano\\.cc/book/cartoon/read\\?book_id=*").match()) 
        {
            //查看章图片
            String url = page.getUrl().get();
            String[] temp = url.split("=");
            String id = temp[1].split("&")[0];
            String episodeId = temp[2];
            
            List<String> urls = page.getHtml().xpath("//div[@id='detail-img-content']/img/@data-lazyload").all();
            if(CollectionUtils.isEmpty(urls))
            {
                return;
            }
            int index = 0;
            List<EpisodeImage> imgs = new ArrayList<>();
            for(String img:urls)
            {
                EpisodeImage episodeImage = new EpisodeImage();
                episodeImage.setId(ID.uuid());
                episodeImage.setCartoonId(id);
                episodeImage.setEpisodeId(episodeId);
                episodeImage.setCode(index);
                episodeImage.setImg(img.split("\\?")[0]);
                
                
                imgs.add(episodeImage);
                index++;
            }
            episodeImageService.save(imgs);
            System.out.println(index);
        }
    }
    
    public Site getSite() {
        return site;
    }

    public void crawl() 
    {
        Request req = new Request();
        req.setMethod(HttpConstant.Method.POST);
        req.setUrl("http://x39957.vrpano.cc/category/bookList?end=0&category_id=0&query=base");
        req.addCookie("PHPSESSID", PHPSESSID);
        req.addCookie("x39957_user", wechatUser);
        
        Map<String, Object> params = new HashMap<>();
        params.put("p", 1);
        params.put("limit", 10);
        params.put("ul", 1);
        params.put("order", "book_view DESC,book_uptime DESC");
        
        req.setRequestBody(HttpRequestBody.form(params, "utf-8"));
        Spider.create(this).addRequest(req).thread(1).run();
    }
    
}
