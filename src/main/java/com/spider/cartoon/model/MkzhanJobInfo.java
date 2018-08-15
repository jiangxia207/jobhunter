//package us.codecraft.jobhunter.model;
//
//import java.util.List;
//
//import org.apache.commons.codec.digest.DigestUtils;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.model.AfterExtractor;
//import us.codecraft.webmagic.model.annotation.ExtractBy;
//import us.codecraft.webmagic.model.annotation.ExtractByUrl;
//import us.codecraft.webmagic.model.annotation.TargetUrl;
//
///**
// * @author code4crafer@gmail.com
// *         Date: 13-6-23
// *         Time: 下午4:28
// */
//@TargetUrl("https://www.mkzhan.com/212722/*.html")
//public class MkzhanJobInfo implements AfterExtractor {
//    @ExtractBy("//div[@id='mainView']/@data-id")
//    private String id;
//    
//    @ExtractBy("//div[@id='mainView']/@data-number")
//    private Integer chapterNo;
//    
//    @ExtractBy("//div[@id='mainView']/@data-title")
//    private String chapterName;
//    
//    @ExtractBy("//div[@id='mainView']/@data-chapter")
//    private String chapterId;
//    
//    @ExtractBy(value = "//div[@id='mainView']//ul//li//img/@data-src", multi = true)
//    private List<String> imgs;
//    
//    
//    @ExtractByUrl
//    private String url;
//    private String urlMd5;
//    
//    private Integer imgSize;
//
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//        this.urlMd5 = DigestUtils.md5Hex(url);
//    }
//
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Integer getChapterNo() {
//        return chapterNo;
//    }
//
//    public void setChapterNo(Integer chapterNo) {
//        this.chapterNo = chapterNo;
//    }
//
//    public String getChapterId() {
//        return chapterId;
//    }
//
//    public void setChapterId(String chapterId) {
//        this.chapterId = chapterId;
//    }
//
//    public String getUrlMd5() {
//        return urlMd5;
//    }
//
//    public void setUrlMd5(String urlMd5) {
//        this.urlMd5 = urlMd5;
//    }
//
//    
//    
//    public String getChapterName() {
//        return chapterName;
//    }
//
//    public void setChapterName(String chapterName) {
//        this.chapterName = chapterName;
//    }
//
//    public List<String> getImgs() {
//        return imgs;
//    }
//
//    public void setImgs(List<String> imgs) {
//        this.imgs = imgs;
//    }
//
//    public void afterProcess(Page page) {
//    }
//
//    public Integer getImgSize() {
//        return imgSize;
//    }
//
//    public void setImgSize(Integer imgSize) {
//        this.imgSize = imgSize;
//    }
//    
//    
//}
