package us.codecraft.jobhunter.pipeline;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.jobhunter.dao.MkzhanDAO;
import us.codecraft.jobhunter.model.MkzhanJobInfo;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午8:56
 */
@Component("mkzhanDaoPipeline")
public class MkzhanDaoPipeline implements PageModelPipeline<MkzhanJobInfo> {

    @Resource
    private MkzhanDAO mkzhanDAO;

    
    public static InputStream inStream = null;
    public static String rootPath = "/Users/Oliver/mkzhan";
    
    
    
    public void process(MkzhanJobInfo mkzhanJobInfo, Task task) {
        
        
        String coursePath = rootPath + "/" + mkzhanJobInfo.getId();
        File file = new File(coursePath);  
        if(!file.exists())
        {  
            file.mkdirs();  
        } 
        
        String chapterPath = coursePath + "/" + mkzhanJobInfo.getChapterId();
        file = new File(chapterPath);
        if(!file.exists())
        {  
            file.mkdirs();  
        } 
        
        
        
        for(int i = 0; i < mkzhanJobInfo.getImgs().size(); i++){
            String link = mkzhanJobInfo.getImgs().get(i);
            try {
                URL url = new URL(link);
                URLConnection con = url.openConnection();
                inStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while((len = inStream.read(buf)) != -1){
                    outStream.write(buf,0,len);
                }
                inStream.close();
                outStream.close();
                file = new File(chapterPath+"/"+i+".jpg");    //图片下载地址
                FileOutputStream op = new FileOutputStream(file);
                op.write(outStream.toByteArray());
                op.close();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        mkzhanJobInfo.setImgSize(mkzhanJobInfo.getImgs().size());
        mkzhanDAO.add(mkzhanJobInfo);
    }
}
