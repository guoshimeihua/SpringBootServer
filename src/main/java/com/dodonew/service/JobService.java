package com.dodonew.service;

import com.dodonew.domain.Job;

import java.util.List;

/**
 * Created by Bruce on 2017/11/1.
 */
public interface JobService {
    public List<Job> getJobList(Integer pageIndex, Integer pageSize);

    public Job getJob(Integer id);

    public boolean addJob(Job job);

    public boolean removeJob(Integer id);

    public boolean modifyJob(Job job);
}
