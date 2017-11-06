package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.annotation.DataValidate;
import com.dodonew.domain.Notice;
import com.dodonew.domain.User;
import com.dodonew.service.NoticeService;
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
 * Created by Bruce on 2017/11/2.
 */
@RestController
public class NoticeController {
    @Autowired
    @Qualifier("noticeService")
    private NoticeService noticeService;
    @Autowired
    @Qualifier("userSevice")
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @RequestMapping(value = "/hrm/api/notices", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"pageIndex"})
    public void selectNoticeList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String pageIndex = requestJson.getString("pageIndex");
            String pageSize = requestJson.getString("pageSize");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }
            List<Notice> noticeList = noticeService.getNoticeList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");
            if (noticeList != null && noticeList.size() > 0) {
                String deptStr = JSON.toJSONString(noticeList, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONArray deptJsonArray = JSONArray.parseArray(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJsonArray);
            } else {
                JSONArray emptyJsonArray = new JSONArray();
                resultJson.put(BootConstants.DATA_KEY, emptyJsonArray);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/notices/id", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"noticeId"})
    public void selectNotice(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        // 为空的情况，在afterCompletion统一做了处理，返回数据为空。
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            String noticeId = requestJson.getString("noticeId");
            Notice notice = noticeService.getNotice(Integer.parseInt(noticeId));
            if (notice == null) {
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                logger.info("notice === " + notice.toString());
                String deptStr = JSON.toJSONString(notice, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/notices", method = RequestMethod.POST)
    @DataValidate(requiredParams = {"title", "content", "userId"})
    public void addNotice(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String title = requestJson.getString("title");
            String content = requestJson.getString("content");
            String userId = requestJson.getString("userId");
            User user = userService.getUser(Integer.parseInt(userId));
            Notice notice = new Notice();
            notice.setTitle(title);
            notice.setContent(content);
            notice.setUser(user);

            boolean isSuccess = noticeService.addNotice(notice);
            System.out.println("isSuccess : " + isSuccess);
            if (isSuccess) {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.MESSAGE_KEY, "通知添加成功");
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "通知添加失败");
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/notices/id", method = RequestMethod.DELETE)
    @DataValidate(requiredParams = {"noticeId"})
    public void deleteNotice(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String noticeId = requestJson.getString("noticeId");
            // 删除一个通知之前，先查询下该部门是否存在
            Notice notice = noticeService.getNotice(Integer.parseInt(noticeId));
            if (notice == null) {
                // 要删除的通知不存在
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "要删除的通知不存在");
            } else {
                boolean isSuccess = noticeService.removeNotice(Integer.parseInt(noticeId));
                if (isSuccess) {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                    resultJson.put(BootConstants.MESSAGE_KEY, "通知删除成功");
                    JSONObject emptyJson = new JSONObject();
                    resultJson.put(BootConstants.DATA_KEY, emptyJson);
                } else {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                    resultJson.put(BootConstants.MESSAGE_KEY, "通知删除失败");
                }
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/notices/id", method = RequestMethod.PATCH)
    @DataValidate(requiredParams = {"noticeId", "title"})
    public void updateNotice(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            String noticeId = requestJson.getString("noticeId");
            String title = requestJson.getString("title");
            Notice notice = new Notice();
            notice.setId(Integer.parseInt(noticeId));
            notice.setTitle(title);
            boolean isSuccess = noticeService.modifyNotice(notice);
            if (isSuccess) {
                Notice modifyedNotice = noticeService.getNotice(Integer.parseInt(noticeId));
                String noticeStr = JSON.toJSONString(modifyedNotice, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(noticeStr);

                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
                resultJson.put(BootConstants.MESSAGE_KEY, "通知信息修改成功");
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "通知信息修改失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }
}
