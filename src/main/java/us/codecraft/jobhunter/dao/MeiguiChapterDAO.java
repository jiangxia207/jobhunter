package us.codecraft.jobhunter.dao;

import org.apache.ibatis.annotations.Insert;

import us.codecraft.jobhunter.model.MeiguiChapter;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:27
 */
public interface MeiguiChapterDAO {

    @Insert("insert into cartoonChapter (`id`,`name`,`code`,`cartoonId`,`imageNo`) values (#{id},#{name},#{code}, #{cartoonId},  #{imageNo})")
    public int add(MeiguiChapter jobInfo);
}
