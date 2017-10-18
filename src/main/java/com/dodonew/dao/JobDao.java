package com.dodonew.dao;

import com.dodonew.domain.Job;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/9/11.
 */
public interface JobDao {
    @Select("select * from " + HrmConstants.JOBTABLE + " where id = #{id}")
    Job selectById(int id);

    @Select("select * from " + HrmConstants.JOBTABLE)
    List<Job> selectAllJob();

    @Delete("delete from " + HrmConstants.JOBTABLE + " where id = #{id}")
    void deleteJob(Integer id);

    @InsertProvider(type = JobDynaSqlProvider.class, method = "insertJob")
    void save(Job job);

    @UpdateProvider(type = JobDynaSqlProvider.class, method = "updateJob")
    void update(Job job);

    @SelectProvider(type = JobDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @SelectProvider(type = JobDynaSqlProvider.class, method = "selectWithParam")
    List<Job> selectByPage(Map<String, Object> params);
}
