//package com.spider.cartoon.localSpider;
//
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.spider.cartoon.common.CommonConstants.SourceType;
//import com.spider.cartoon.extract.MeiguiExtract;
//import com.spider.cartoon.model.MeiguiChapter;
//import com.spider.cartoon.repository.Cartoon;
//import com.spider.cartoon.repository.Cartoon.ProgressType;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Request;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.model.HttpRequestBody;
//import us.codecraft.webmagic.processor.PageProcessor;
//import us.codecraft.webmagic.selector.Selectable;
//import us.codecraft.webmagic.utils.HttpConstant;
//
//public class LocalMeiguiSpider implements PageProcessor{
//
//    
////    private static String wechatUser="Y4%2FKTElnzxTiR6zviWaFM%2Fhd2Yzv0MQVx4DaT%2Fb7l6ni0GRKRZz4t2zwtZ%2BTpuY7iFwLiPnfT%2BQM495A6B0vtq3coOMdGVuen78BHaFeak6zMAZWYR7niegUDdYhEKFysIV0AoayU0KMy7I7tG6dOPhfsxh5KcF8xOj74BQCRTBSGgqq6%2B3mQvGfQL8Cc%2F42mXCqfZGMY%2B%2B9dRUecZVHCbkH%2F%2Fqpjy4SQi5C0QIGoTcyHyuKF3s5s3fz1JELNM927XUz%2BHLfmLUuPQcjNNqjSzrGNTxH1YmMrb5I3K9O%2BJoeTOUoZUJFlSu4UfsIQX4lE9h0SYQvIzq3pFFQKq26uc6Ze0FrqlAcuHACxfp4fIYAWaBxJiJFARvAcgv0TIk842QuVDDUYN0%3D";
////    private static String PHPSESSID = "eb9v0cosalikl9rs22pkuhtob2";
//    private static String wechatUser="fpadwgJ0L2On9CTXizR3u35qqjyJKksX%2Fnk2Fs9LCntQPXVja8FSu1IWWD7Tdesk6taEAPjQbVy4cI%2BCue9bBRNkyW7bOYvrLtlffzcpJ2UVgRRJ5VjvPglEe%2F9Xd3YC6z8cvFViwYPIunn82QYbQ5KxRBlQNyk2XtS389F1Lz2EF%2FzzGD4ulfcfjHwbDkgXf9az9HMXKZcMdKPzIUkSbB6IfR4LjxKOC2%2BqtSEpUcNBzH42s%2Bw7fKnFBuel%2FvFMWFhlek4RYk1n9IhBaKqSdB43FFZZkcKGrAmB6C2z62rc4JUaMv%2FftNBxpZEJwoC%2BPVlZNmYwSF7Ggf4pOYS2QazsFRzQzz1%2B%2Byf2tqm1yP0F53bsEWRV637VcKE0V0MynGWhK5ou8hA%3D";
//    private static String PHPSESSID = "rt4scg4nedqmd7lb9om75hq686";
//    
//    
//    
//    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000).setTimeOut(10000).setRetryTimes(0)
//            .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; HUAWEI NXT-TL00 Build/HUAWEINXT-TL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044203 Mobile Safari/537.36 MicroMessenger/6.6.3.1260(0x26060339) NetType/WIFI Language/zh_CN");
//
//
//    private static final String detailPrefix = "http://x39957.vrpano.cc";
//    private static final String chaperPrefix = "http://x39957.vrpano.cc/book/cartoon/read?book_id={0}&chapter_id={1}";
//    
//    //private static final String detailPattern = "http://x39957.yuntoon.com/book/cartoon/info?book_id=65";
//    
//    private Map<String, Set<String>> urlTotal = new HashMap<>();
//    private Map<String, MeiguiChapter> chapterInfoMap = new HashMap<>();
//    private Set<String> cartoonMap = new HashSet<>();
//            
//    public void process(Page page) 
//    {
//        if (page.getUrl().regex("http://x39957\\.vrpano\\.cc/category/bookList*").match()) 
//        {
//            //初始页
//            List<String> urls = page.getHtml().xpath("//li[@class='w-shelfBookinfo']/a/@href").all();
//            if(CollectionUtils.isEmpty(urls))
//            {
//                return;
//            }
//            
//            for(String url:urls)
//            {
//                if(urlTotal.keySet().contains(url))
//                {
//                    continue;
//                }
//                String id = url.replace("/book/cartoon/info?book_id=", "");
//                urlTotal.put(id, new HashSet<>() );
//                Request req = new Request();
//                req.setUrl(detailPrefix+url);
//                req.setMethod(HttpConstant.Method.GET);
//                req.addCookie("PHPSESSID", PHPSESSID);
//                req.addCookie("x39957_user", wechatUser);
//                page.addTargetRequest(req);
//            }
//            
//            
//            Request req = new Request();
//            req.setUrl(page.getUrl().get());
//            req.setMethod(HttpConstant.Method.POST);
//            req.addCookie("PHPSESSID", PHPSESSID);
//            req.addCookie("x39957_user", wechatUser);
//            
//            
//            String params = new String(page.getRequest().getRequestBody().getBody());
//            String[] x = params.split("\\&");
//            int pageNo =  Integer.parseInt(x[0].split("=")[1]);
//            pageNo++;
//            
//            Map<String, Object> newParams = new HashMap<>();
//            newParams.put("p", pageNo);
//            newParams.put("limit", 10);
//            newParams.put("ul", 1);
//            newParams.put("order", "book_view DESC,book_uptime DESC");
//            
//            req.setRequestBody(HttpRequestBody.form(newParams, "utf-8"));
//            
//            page.addTargetRequest(req);
//            
//        }
//        else if (page.getUrl().regex("http://x39957\\.vrpano\\.cc/book/cartoon/info\\?book_id=*").match()) 
//        {
//            
//            //此为查看详情
//            Selectable aax = page.getUrl().replace("http://x39957\\.vrpano\\.cc/book/cartoon/info\\?book_id=", "");
//            String id = aax.all().get(0);
//            List<String> chapterNames = page.getHtml().xpath("//ul[@class='book-chapter-list']/li/p/@onclick/text()").all();
//            List<String> chapterIds = page.getHtml().xpath("//ul[@class='book-chapter-list']/li/p/@onclick").all();
//           
//            Cartoon cartoon = MeiguiExtract.extractDetail(page.getHtml(), id);
//            
//            int index = 0;
//            for(String chapterIdInfo:chapterIds)
//            {
//                String chapterId = chapterIdInfo.split("'")[1].split("chapter_id=")[1];
//                
//                Request req = new Request();
//                req.setUrl(MessageFormat.format(chaperPrefix, id, chapterId));
//                req.setMethod(HttpConstant.Method.POST);
//                req.addCookie("PHPSESSID", PHPSESSID);
//                req.addCookie("x39957_user", wechatUser);
//                
//                MeiguiChapter chapter = new MeiguiChapter();
//                chapter.setId(chapterId);
//                chapter.setCartoonId(id);
//                chapter.setName(chapterNames.get(index));
//                chapter.setCode(index++);
//                chapterInfoMap.put(id+","+chapterId, chapter);
//                
//                page.addTargetRequest(req);
//            }
//        }
////        else if (page.getUrl().regex("http://x39957\\.yuntoon\\.com/book/chapters\\?book_id=*").match()) 
////        {
////            //查看章节
////            Selectable aax = page.getUrl().replace("http://x39957\\.yuntoon\\.com/book/chapters\\?book_id=", "");
////            String id = aax.all().get(0);
////            
////            
////            List<String> urls = page.getHtml().xpath("//a[@class='orderid']/@href").all();
////            if(CollectionUtils.isEmpty(urls))
////            {
////                return;
////            }
////            
////            for(String url:urls)
////            {
////                if(urlTotal.keySet().contains(url))
////                {
////                    continue;
////                }
////                urlTotal.put(id, new HashSet<>() );
////                Request req = new Request();
////                req.setUrl(detailPrefix+url);
////                req.setMethod(HttpConstant.Method.GET);
////                req.addCookie("PHPSESSID", PHPSESSID);
////                req.addCookie("x39957_user", wechatUser);
////                
////                page.addTargetRequest(req);
////                
////            }
////        }
//        else if (page.getUrl().regex("http://x39957\\.vrpano\\.cc/book/cartoon/read\\?book_id=*").match()) 
//        {
//            //查看章图片
//            String url = page.getUrl().get();
//            String[] temp = url.split("=");
//            String id = temp[1].split("&")[0];
//            String chapterId = temp[2];
//            
//            List<String> urls = page.getHtml().xpath("//div[@id='detail-img-content']/img/@data-lazyload").all();
//            if(CollectionUtils.isEmpty(urls))
//            {
//                return;
//            }
//            MeiguiChapter chapter = chapterInfoMap.get(id+","+chapterId);
//            if(chapter == null)
//            {
//                return;
//            }
//            else
//            {
//                chapter.setImageNo(urls.size());
//                //meiguiChapterDAO.add(chapter);
//            }
//            
//            
//            urlTotal.get(id).addAll(urls);
//        }
//    }
//    
//    public Site getSite() {
//        return site;
//    }
//
//    public void crawl() 
//    {
//        Request req = new Request();
//        req.setMethod(HttpConstant.Method.POST);
//        req.setUrl("http://x39957.vrpano.cc/category/bookList?end=0&category_id=0&query=base");
//        req.addCookie("PHPSESSID", PHPSESSID);
//        req.addCookie("x39957_user", wechatUser);
//        
//        Map<String, Object> params = new HashMap<>();
//        params.put("p", 1);
//        params.put("limit", 10);
//        params.put("ul", 1);
//        params.put("order", "book_view DESC,book_uptime DESC");
//        
//        req.setRequestBody(HttpRequestBody.form(params, "utf-8"));
//        Spider.create(this).addRequest(req).thread(1).run();
//    }
//    
//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/local/applicationContext*.xml");
//        
//        final LocalMeiguiSpider meiguiSpider = applicationContext.getBean(LocalMeiguiSpider.class);
//        meiguiSpider.crawl();
//        
//        
//    }
//}
