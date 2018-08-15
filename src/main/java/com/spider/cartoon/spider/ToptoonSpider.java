package com.spider.cartoon.spider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.spider.cartoon.model.MeiguiChapter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

@Component
public class ToptoonSpider implements PageProcessor{

    
    
    
    
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000).setRetryTimes(0)
            .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; HUAWEI NXT-TL00 Build/HUAWEINXT-TL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044203 Mobile Safari/537.36 MicroMessenger/6.6.3.1260(0x26060339) NetType/WIFI Language/zh_CN");


    public static int pageNum = 1;

    private static final String detailPrefix = "http://m.toptoon.vip/comic/contents_list?comic_id=";
    private static final String chaperPrefix = "http://x39957.yuntoon.com/book/cartoon/read?book_id={0}&chapter_id={1}";
    private static final String pageSuffix="&limit=10&ul=&order=book_view+DESC%2Cbook_uptime+DESC";
    
    //private static final String detailPattern = "http://x39957.yuntoon.com/book/cartoon/info?book_id=65";
    
    private Map<String, Set<String>> urlTotal = new HashMap<>();
    private Map<String, MeiguiChapter> chapterInfoMap = new HashMap<>();
    private Set<String> cartoonMap = new HashSet<>();
            
    public void process(Page page) 
    {
        System.out.println(page.getUrl());
        if (page.getUrl().regex("http://m\\.toptoon\\.vip/page/mainpage").match()) 
        {
            //初始页
//            Selectable postHtml = page.getHtml().replace("class=\"\"", "class=\"jiangxia\"");
//            List<String> urls = postHtml.xpath("//div[@class='jiangxia']/onclick").all();
            
            List<String> urls = page.getHtml().regex("call_page\\(\\'contents_\\d{2,7}\\'\\)").all();
            
            if(CollectionUtils.isEmpty(urls))
            {
                return;
            }
            
            for(String url:urls)
            { 
                String[] x = url.split("_");
                String id = x[2].split("'")[0];
                
                Request req = new Request();
                req.setUrl(detailPrefix+id);
                req.setMethod(HttpConstant.Method.GET);
                this.addCookie(req);
                page.addTargetRequest(req);
            }
        }
        else if (page.getUrl().regex("http://m\\.toptoon\\.vip/comic/contents_list*").match()) 
        {
            List<String> urls = page.getHtml().regex("episode_click\\(\\'\\d{4,7}\\',\\s\\'[0-9a-z]{1,60}\\'\\)").all();
            System.out.println(urls);
        }
            
    }
    
    public Site getSite() {
        return site;
    }

    
    public void crawl() 
    {
        Request req = new Request();
        req.setMethod(HttpConstant.Method.GET);
        req.setUrl("http://m.toptoon.vip/page/mainpage?tmp=1_undefined_mobile");
        
        Spider.create(this).addRequest(req).thread(1).run();
    }
    
    private void addCookie(Request req)
    {
        req.addCookie("PHPSESSID", "s4ma53gt3n6lqecjvlus3qu7o7");
        req.addCookie("onethink_home_save_id", "jiangxia207%40163.com");
        req.addCookie("onethink_home_user_auth", "MDAwMDAwMDAwMMusfWKUvISfsp96qX55fWF_fK-orZ6K4MCJfKeOuWTRr52impTSn57Fo5Vlkox1qn59ynaxhI3gsp6AqJe1fNivoKuZlpiFnMV9ap-SjGWXlYDSqb6DfZyvm3djgtyE3bSHdGGBuZqfyYdubg");
        req.addCookie("onethink_home_user_auth_sign", "fca68ad7f92cc043f6b9006c6eacbf771b15c341");
        req.addCookie("auto_key", "1");
        req.addCookie("p_id", null);
        req.addCookie("adult_check", "1");
        req.addCookie("Hm_lvt_993a035cb4815213987c94caa4109feb", "1534152130");
        req.addCookie("Hm_lpvt_993a035cb4815213987c94caa4109feb", "1534170557");
        req.addCookie("view_mode_page", "complete");
        req.addCookie("user_idx", "10344");
    }
    
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        
        final ToptoonSpider meiguiSpider = applicationContext.getBean(ToptoonSpider.class);
        meiguiSpider.crawl();
        
        
        
    }
}
