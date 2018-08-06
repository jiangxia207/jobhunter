package us.codecraft.jobhunter.dao;

import org.apache.ibatis.annotations.Insert;

import us.codecraft.jobhunter.model.MeiguiChapterImage;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:27
 */
public interface MeiguiChapterImageDAO {

    @Insert("insert into cartoonChapterImage (`id`,`chapterId`,`image`, `code`) values (#{id},#{chapterId},#{image}, #{code})")
    public int add(MeiguiChapterImage jobInfo);
}
