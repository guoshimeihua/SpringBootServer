package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.annotation.DataValidate;
import com.dodonew.domain.User;
import com.dodonew.service.UserService;
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
public class UserController {
    @Autowired
    @Qualifier("userSevice")
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 在查询列表的时候，有这样一个情况：如果当前页面是否超过了总页数:如果超过了默认给最后一页作为当前页。
     */
    @RequestMapping(value = "/hrm/api/users", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"pageIndex"})
    public void selectUserList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String pageIndex = requestJson.getString("pageIndex");
            String pageSize = requestJson.getString("pageSize");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }
            List<User> userList = userService.getUserList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");
            if (userList != null && userList.size() > 0) {
                String deptStr = JSON.toJSONString(userList, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
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
     * 获取指定用户的信息
     */
    @RequestMapping(value = "/hrm/api/users/id", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"userId"})
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        // 为空的情况，在afterCompletion统一做了处理，返回数据为空。
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            String deptId = requestJson.getString("userId");
            User user = userService.getUser(Integer.parseInt(deptId));
            if (user == null) {
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                logger.info("dept === " + user.toString());
                String deptStr = JSON.toJSONString(user, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /***
     * 添加用户
     */
    @RequestMapping(value = "/hrm/api/users", method = RequestMethod.POST)
    @DataValidate(requiredParams = {"loginName", "password"})
    public void addUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String loginName = requestJson.getString("loginName");
            String password = requestJson.getString("password");
            User user = new User();
            user.setLoginName(loginName);
            user.setPassword(password);

            boolean isSuccess = userService.addUser(user);
            System.out.println("isSuccess : " + isSuccess);
            if (isSuccess) {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.MESSAGE_KEY, "用户添加成功");
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "用户添加失败");
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /**
     * 删除某个指定用户的信息
     */
    @RequestMapping(value = "/hrm/api/users/id", method = RequestMethod.DELETE)
    @DataValidate(requiredParams = {"userId"})
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String userId = requestJson.getString("userId");
            // 删除一个用户之前，先查询下该部门是否存在
            User user = userService.getUser(Integer.parseInt(userId));
            if (user == null) {
                // 要删除的用户不存在
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "要删除的用户不存在");
            } else {
                boolean isSuccess = userService.removeUser(Integer.parseInt(userId));
                if (isSuccess) {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                    resultJson.put(BootConstants.MESSAGE_KEY, "用户删除成功");
                    JSONObject emptyJson = new JSONObject();
                    resultJson.put(BootConstants.DATA_KEY, emptyJson);
                } else {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                    resultJson.put(BootConstants.MESSAGE_KEY, "用户删除失败");
                }
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/users/id", method = RequestMethod.PATCH)
    @DataValidate(requiredParams = {"userId"})
    public void updateUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            String userId = requestJson.getString("userId");
            String userName = requestJson.getString("userName");
            String password = requestJson.getString("password");
            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setUserName(userName);
            user.setPassword(password);
            boolean isSuccess = userService.modifyUser(user);
            if (isSuccess) {
                User modifyedUser = userService.getUser(Integer.parseInt(userId));
                String deptStr = JSON.toJSONString(modifyedUser, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);

                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
                resultJson.put(BootConstants.MESSAGE_KEY, "用户信息修改成功");
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "用户信息修改失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/users/id", method = RequestMethod.PUT)
    @DataValidate(requiredParams = {"userId"})
    public void modifyUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            String userId = requestJson.getString("userId");
            String userName = requestJson.getString("userName");
            String password = requestJson.getString("password");
            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setUserName(userName);
            user.setPassword(password);
            boolean isSuccess = userService.modifyUser(user);
            if (isSuccess) {
                User modifyedUser = userService.getUser(Integer.parseInt(userId));
                String deptStr = JSON.toJSONString(modifyedUser, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);

                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
                resultJson.put(BootConstants.MESSAGE_KEY, "用户信息修改成功");
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "用户信息修改失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }
}
