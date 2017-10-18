package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dodonew.annotation.DataValidate;
import com.dodonew.domain.Dept;
import com.dodonew.service.DeptService;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.common.StatusCode;
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
 * Created by Bruce on 2017/10/17.
 */
@RestController
public class DeptController {
    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    @Qualifier("deptService")
    private DeptService deptService;

    /**
     * 在查询列表的时候，有这样一个情况：如果当前页面是否超过了总页数:如果超过了默认给最后一页作为当前页。
     */
    @RequestMapping(value = "/hrm/api/depts", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"pageIndex"})
    public void selectDeptList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String pageIndex = requestJson.getString("pageIndex");
            String pageSize = requestJson.getString("pageSize");

            List<Dept> deptList = deptService.findDeptList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");
            if (deptList != null && deptList.size() > 0) {
                String deptStr = JSON.toJSONString(deptList);
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
     * 获取指定部门的信息
     */
    @RequestMapping(value = "/hrm/api/depts/id", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"deptId"})
    public void selectDept(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        // 为空的情况，在afterCompletion统一做了处理，返回数据为空。
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            String deptId = requestJson.getString("deptId");
            Dept dept = deptService.findDept(Integer.parseInt(deptId));
            if (dept == null) {
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                logger.info("dept === " + dept.toString());
                String deptStr = JSON.toJSONString(dept);
                JSONObject deptJson = JSONObject.parseObject(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /***
     * 新建一个部门
     */
    @RequestMapping(value = "/hrm/api/depts", method = RequestMethod.POST)
    @DataValidate(requiredParams = {"departName"})
    public void addDept(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String departName = requestJson.getString("departName");
            String remark = requestJson.getString("remark");
            Dept dept = new Dept();
            dept.setDepartName(departName);
            dept.setRemark(remark);

            // 如果部门插入不成功的话，会抛出异常的。下面的代码就不会在执行了，所以不用再去查询是否查询成功了。
            // 因为是模糊查询，无法把当前部门的信息返回给客户端。
            deptService.addDept(dept);
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "部门添加成功");
            JSONObject emptyJson = new JSONObject();
            resultJson.put(BootConstants.DATA_KEY, emptyJson);

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /**
     * 删除某个指定部门的信息
     */
    @RequestMapping(value = "/hrm/api/depts/id", method = RequestMethod.DELETE)
    @DataValidate(requiredParams = {"deptId"})
    public void deleteDept(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String deptId = requestJson.getString("deptId");
            // 删除一个部门之前，先查询下该部门是否存在
            Dept dept = deptService.findDept(Integer.parseInt(deptId));
            if (dept == null) {
                // 要删除的部门不存在
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "要删除的部门不存在");
            } else {
                deptService.removeDept(Integer.parseInt(deptId));
                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.MESSAGE_KEY, "部门删除成功");
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/depts/id", method = RequestMethod.PATCH)
    @DataValidate(requiredParams = {"deptId"})
    public void updateDept(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String deptId = requestJson.getString("deptId");
            String departName = requestJson.getString("departName");
            String remark = requestJson.getString("remark");
            Dept dept = new Dept();
            dept.setId(Integer.parseInt(deptId));
            dept.setDepartName(departName);
            dept.setRemark(remark);

            deptService.modifyDept(dept);
            Dept modifyedDept = deptService.findDept(Integer.parseInt(deptId));
            String deptStr = JSON.toJSONString(modifyedDept);
            JSONObject deptJson = JSONObject.parseObject(deptStr);

            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.DATA_KEY, deptJson);
            resultJson.put(BootConstants.MESSAGE_KEY, "部门信息修改成功");

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/depts/id", method = RequestMethod.PUT)
    @DataValidate(requiredParams = {"deptId"})
    public void modifyDept(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String deptId = requestJson.getString("deptId");
            String departName = requestJson.getString("departName");
            String remark = requestJson.getString("remark");
            Dept dept = new Dept();
            dept.setId(Integer.parseInt(deptId));
            dept.setDepartName(departName);
            dept.setRemark(remark);

            deptService.modifyDept(dept);
            Dept modifyedDept = deptService.findDept(Integer.parseInt(deptId));
            String deptStr = JSON.toJSONString(modifyedDept);
            JSONObject deptJson = JSONObject.parseObject(deptStr);

            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.DATA_KEY, deptJson);
            resultJson.put(BootConstants.MESSAGE_KEY, "部门信息修改成功");

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }
}
