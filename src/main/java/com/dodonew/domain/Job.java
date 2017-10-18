package com.dodonew.domain;

import java.io.Serializable;

/**
 * Created by Bruce on 2017/9/6.
 */
public class Job implements Serializable {
    private static final long serialVersionUID = 1216145470144818152L;
    private Integer id;
    private String jobName;
    private String remark;

    public Job() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getJobName() {
        return jobName;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
