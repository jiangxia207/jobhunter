package us.codecraft.jobhunter.dao;

import org.apache.ibatis.annotations.Insert;

import us.codecraft.jobhunter.model.MeiguiCartoon;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:27
 */
public interface MeiguiDAO {

    @Insert("insert into cartoon (`id`,`name`) values (#{id},#{name})")
    public int add(MeiguiCartoon jobInfo);
}
