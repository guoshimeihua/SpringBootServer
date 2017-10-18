package com.dodonew.dao;

import com.dodonew.domain.Job;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by Bruce on 2017/9/11.
 */
public class JobDynaSqlProvider {
    public String insertJob(final Job job) {
        return new SQL () {
            {
                INSERT_INTO(HrmConstants.JOBTABLE);
                if (job.getJobName() != null && !"".equals(job.getJobName())) {
                    VALUES("jobname", "#{jobName}");
                }
                if (job.getRemark() != null && !"".equals(job.getRemark())) {
                    VALUES("remark", "#{remark}");
                }
            }
        }.toString();
    }

    public String updateJob(final Job job) {
        return new SQL() {
            {
                UPDATE(HrmConstants.JOBTABLE);
                if (job.getJobName() != null && !"".equals(job.getJobName())) {
                    SET("jobname = #{jobName}");
                }
                if (job.getRemark() != null && !"".equals(job.getRemark())) {
                    SET("remark = #{remark}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String count(final Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(HrmConstants.JOBTABLE);
                if (params.get("job") != null) {
                    Job job = (Job) params.get("job");
                    if (job.getJobName() != null && "".equals(job.getJobName())) {
                        WHERE(" jobname like concat ('%', #{job.jobName}, '%')");
                    }
                }
            }
        }.toString();
    }

    public String selectWithParam(final Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(HrmConstants.JOBTABLE);
                if (params.get("job") != null) {
                    Job job = (Job) params.get("job");
                    if (job.getJobName() != null && !"".equals(job.getJobName())) {
                        WHERE("jobname like concat ('%', #{job.jobName}, '%')");
                    }
                }
            }
        }.toString();

        if (params.get("pageModel") != null) {
            sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize} ";
        }
        return sql;
    }
}
