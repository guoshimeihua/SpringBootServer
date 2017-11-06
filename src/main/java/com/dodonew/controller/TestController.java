package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/10/16.
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/api/employees", method = RequestMethod.GET)
    //@DataValidate(requiredParams = {"employeeId", "employeeName"})
    public void findEmployee(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (requestJson != null) {
            // 拿到传递过来的数据，在这里处理相关的业务逻辑
            logger.info("requestJson ===== " + requestJson);

            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            Map<String, Object> map1 = new HashMap<>();
            map1.put("name", "张三");
            map1.put("age", 29);
            map1.put("address", "北京市朝阳区");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("name", "李四");
            map2.put("age", 29);
            map2.put("address", "上海市杨浦区");

            Map<String, Object> map3 = new HashMap<>();
            map3.put("name", "王五");
            map3.put("age", 29);
            map3.put("address", "深圳南山区");

            List<Map<String, Object>> users = new ArrayList<>();
            users.add(map1);
            users.add(map2);
            users.add(map3);

            String data = JSON.toJSONString(map1, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
            JSONObject jsonObject = JSONObject.parseObject(data);
            resultJson.put(BootConstants.DATA_KEY, jsonObject);

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }
}
