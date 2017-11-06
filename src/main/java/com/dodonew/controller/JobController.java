package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.annotation.DataValidate;
import com.dodonew.domain.Job;
import com.dodonew.service.JobService;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.common.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Bruce on 2017/11/1.
 */
@RestController
public class JobController {
    @Autowired
    @Qualifier("jobService")
    private JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    /**
     * 在查询列表的时候，有这样一个情况：如果当前页面是否超过了总页数:如果超过了默认给最后一页作为当前页。
     */
    @RequestMapping(value = "/hrm/api/jobs", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"pageIndex"})
    public void selectJobList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String pageIndex = requestJson.getString("pageIndex");
            String pageSize = requestJson.getString("pageSize");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }
            List<Job> jobList = jobService.getJobList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");
            if (jobList != null && jobList.size() > 0) {
                String deptStr = JSON.toJSONString(jobList, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONArray deptJsonArray = JSONArray.parseArray(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJsonArray);
            } else {
                JSONArray emptyJsonArray = new JSONArray();
                resultJson.put(BootConstants.DATA_KEY, emptyJsonArray);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /**
     * 获取指定职位的信息
     */
    @RequestMapping(value = "/hrm/api/jobs/id", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"jobId"})
    public void selectJob(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        // 为空的情况，在afterCompletion统一做了处理，返回数据为空。
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            String jobId = requestJson.getString("jobId");
            Job job = jobService.getJob(Integer.parseInt(jobId));
            if (job == null) {
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                logger.info("job === " + job.toString());
                String deptStr = JSON.toJSONString(job, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /***
     * 新建一个职位
     */
    @RequestMapping(value = "/hrm/api/jobs", method = RequestMethod.POST)
    @DataValidate(requiredParams = {"jobName"})
    public void addJob(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String jobName = requestJson.getString("jobName");
            String remark = requestJson.getString("remark");
            Job job = new Job();
            job.setJobName(jobName);
            job.setRemark(remark);

            boolean isSuccess = jobService.addJob(job);
            System.out.println("isSuccess : " + isSuccess);
            if (isSuccess) {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.MESSAGE_KEY, "职位添加成功");
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "职位添加失败");
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /**
     * 删除某个指定部门的信息
     */
    @RequestMapping(value = "/hrm/api/jobs/id", method = RequestMethod.DELETE)
    @DataValidate(requiredParams = {"jobId"})
    public void deleteJob(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String jobId = requestJson.getString("jobId");
            // 删除一个职位之前，先查询下该部门是否存在
            Job job = jobService.getJob(Integer.parseInt(jobId));
            if (job == null) {
                // 要删除的职位不存在
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "要删除的职位不存在");
            } else {
                boolean isSuccess = jobService.removeJob(Integer.parseInt(jobId));
                if (isSuccess) {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                    resultJson.put(BootConstants.MESSAGE_KEY, "职位删除成功");
                    JSONObject emptyJson = new JSONObject();
                    resultJson.put(BootConstants.DATA_KEY, emptyJson);
                } else {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                    resultJson.put(BootConstants.MESSAGE_KEY, "职位删除失败");
                }
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/jobs/id", method = RequestMethod.PATCH)
    @DataValidate(requiredParams = {"jobId"})
    public void updateJob(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String jobId = requestJson.getString("jobId");
            String jobName = requestJson.getString("jobName");
            String remark = requestJson.getString("remark");
            Job job = new Job();
            job.setId(Integer.parseInt(jobId));
            job.setJobName(jobName);
            job.setRemark(remark);

            boolean isSuccess = jobService.modifyJob(job);
            if (isSuccess) {
                Job modifyedJob = jobService.getJob(Integer.parseInt(jobId));
                String jobStr = JSON.toJSONString(modifyedJob, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(jobStr);

                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
                resultJson.put(BootConstants.MESSAGE_KEY, "职位信息修改成功");
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "职位信息修改失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }
}
