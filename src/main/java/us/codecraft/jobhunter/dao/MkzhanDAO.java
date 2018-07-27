package us.codecraft.jobhunter.dao;

import org.apache.ibatis.annotations.Insert;

import us.codecraft.jobhunter.model.MkzhanJobInfo;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:27
 */
public interface MkzhanDAO {

    @Insert("insert into Mkzhan (`id`,`chapterNo`,`chapterName`,`chapterId`,`url`,`urlMd5`, `imgSize`) values (#{id},#{chapterNo},#{chapterName},#{chapterId},#{url},#{urlMd5}, #{imgSize})")
    public int add(MkzhanJobInfo mkzhanJobInfo);
}
