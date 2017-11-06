package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.annotation.DataValidate;
import com.dodonew.domain.Dept;
import com.dodonew.domain.Employee;
import com.dodonew.domain.Job;
import com.dodonew.service.DeptService;
import com.dodonew.service.EmployeeService;
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
public class EmployeeController {
    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;
    @Autowired
    @Qualifier("deptService")
    private DeptService deptService;
    @Autowired
    @Qualifier("jobService")
    private JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @RequestMapping(value = "/hrm/api/employees", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"pageIndex"})
    public void selectEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String pageIndex = requestJson.getString("pageIndex");
            String pageSize = requestJson.getString("pageSize");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }
            List<Employee> employeeList = employeeService.getEmployeeList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");
            if (employeeList != null && employeeList.size() > 0) {
                String deptStr = JSON.toJSONString(employeeList, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect);
                JSONArray deptJsonArray = JSONArray.parseArray(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJsonArray);
            } else {
                JSONArray emptyJsonArray = new JSONArray();
                resultJson.put(BootConstants.DATA_KEY, emptyJsonArray);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/employees/id", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"employeeId"})
    public void selectEmployee(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        // 为空的情况，在afterCompletion统一做了处理，返回数据为空。
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            String employeeId = requestJson.getString("employeeId");
            Employee employee = employeeService.getEmployee(Integer.parseInt(employeeId));
            if (employee == null) {
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                logger.info("employee === " + employee.toString());
                String deptStr = JSON.toJSONString(employee, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/employees", method = RequestMethod.POST)
    @DataValidate(requiredParams = {"name", "cardId", "deptId", "jobId", "education", "phone", "address", "email"})
    public void addEmployee(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String name = requestJson.getString("name");
            String cardId = requestJson.getString("cardId");
            String education = requestJson.getString("education");
            String phone = requestJson.getString("phone");
            String deptId = requestJson.getString("deptId");
            String jobId = requestJson.getString("jobId");
            String address = requestJson.getString("address");
            String email = requestJson.getString("email");
            Dept dept = deptService.findDept(Integer.parseInt(deptId));
            Job job = jobService.getJob(Integer.parseInt(jobId));

            Employee employee = new Employee();
            employee.setName(name);
            employee.setCardId(cardId);
            employee.setEducation(education);
            employee.setPhone(phone);
            employee.setDept(dept);
            employee.setJob(job);
            employee.setAddress(address);
            employee.setEmail(email);

            boolean isSuccess = employeeService.addEmployee(employee);
            System.out.println("isSuccess : " + isSuccess);
            if (isSuccess) {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.MESSAGE_KEY, "员工添加成功");
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "员工添加失败");
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/employees/id", method = RequestMethod.DELETE)
    @DataValidate(requiredParams = {"employeeId"})
    public void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String employeeId = requestJson.getString("employeeId");
            // 删除一个员工之前，先查询下该员工是否存在
            Employee employee = employeeService.getEmployee(Integer.parseInt(employeeId));
            if (employee == null) {
                // 要删除的员工不存在
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "要删除的员工不存在");
            } else {
                boolean isSuccess = employeeService.removeEmployee(Integer.parseInt(employeeId));
                if (isSuccess) {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                    resultJson.put(BootConstants.MESSAGE_KEY, "员工删除成功");
                    JSONObject emptyJson = new JSONObject();
                    resultJson.put(BootConstants.DATA_KEY, emptyJson);
                } else {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                    resultJson.put(BootConstants.MESSAGE_KEY, "员工删除失败");
                }
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/employees/id", method = RequestMethod.PATCH)
    @DataValidate(requiredParams = {"employeeId"})
    public void updateEmployee(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String employeeId = requestJson.getString("employeeId");
            String hobby = requestJson.getString("hobby");
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(employeeId));
            employee.setHobby(hobby);

            boolean isSuccess = employeeService.modifyEmployee(employee);
            if (isSuccess) {
                Employee modifyedEmployee = employeeService.getEmployee(Integer.parseInt(employeeId));
                String employeeStr = JSON.toJSONString(modifyedEmployee, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(employeeStr);

                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
                resultJson.put(BootConstants.MESSAGE_KEY, "员工信息修改成功");
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "员工信息修改失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }
}
