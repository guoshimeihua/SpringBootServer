package com.dodonew.service;

import com.dodonew.dao.JobDao;
import com.dodonew.domain.Job;
import com.dodonew.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/11/1.
 */
@Service("jobService")
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;

    @Override
    public List<Job> getJobList(Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        if (pageSize <= 0) {
            pageSize = 10;
        }
        pageModel.setPageSize(pageSize);
        Integer count = jobDao.count(params);
        pageModel.setRecordCount(count);
        params.put("pageModel", pageModel);
        return jobDao.selectByPage(params);
    }

    @Override
    public Job getJob(Integer id) {
        return jobDao.selectById(id);
    }

    @Override
    public boolean addJob(Job job) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = jobDao.save(job);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean removeJob(Integer id) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = jobDao.deleteJob(id);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean modifyJob(Job job) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = jobDao.update(job);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
