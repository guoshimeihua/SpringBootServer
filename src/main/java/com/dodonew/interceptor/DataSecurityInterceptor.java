package com.dodonew.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dodonew.annotation.DataValidate;
import com.dodonew.handler.RequestHandler;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.common.StatusCode;
import com.dodonew.util.security.AESUtil;
import com.dodonew.util.security.MD5Util;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Bruce on 2017/10/16.
 */
public class DataSecurityInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DataSecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("在请求处理前进行调用");

        // AES加解密
        RequestHandler requestHandler = new RequestHandler(request);
        JSONObject jsonObject = requestHandler.getJSON();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");

        // 时间校验
        long time = jsonObject.getLong("timeStamp");
        long sysTime = System.currentTimeMillis();
        if (Math.abs(sysTime-time) > 1000 * 60 * 5) {
            logger.info("请求时间不合法");
            // 在这里返回错误的信息给客户端
            JSONObject responseJson = new JSONObject();
            responseJson.put(BootConstants.CODE_KEY, StatusCode.ERROR_TIMEOUT);
            responseJson.put(BootConstants.MESSAGE_KEY, "请求时间不合法");
            String responseStr = AESUtil.encrypt(responseJson.toString(), BootConstants.AES_KEY, BootConstants.AES_IV);
            response.getWriter().write(responseStr);
            return false;
        }

        // 对数据签名进行校验 需要的是sortedMap，看看这样转换是不是可以的
        String sign = jsonObject.getString("sign");
        Map<String, Object> map = JSONObject.toJavaObject(jsonObject, Map.class);
        // TreeMap默认是升序排列的，如果要改为降序采用其他方式来实现
        SortedMap<String, Object> sortedMap = new TreeMap<>(map);
        String sysSign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        if (!sysSign.equals(sign)) {
            logger.info("数据签名校验失败");
            // 在这里返回错误的信息给客户端
            JSONObject responseJson = new JSONObject();
            responseJson.put(BootConstants.CODE_KEY, StatusCode.ERROR_SIGN_INVALIDATE);
            responseJson.put(BootConstants.MESSAGE_KEY, "数据签名校验失败");
            String responseStr = AESUtil.encrypt(responseJson.toString(), BootConstants.AES_KEY, BootConstants.AES_IV);
            response.getWriter().write(responseStr);
            return false;
        }

        // 对必须要传的参数也给进行下校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 注解接口
            DataValidate annotation = method.getAnnotation(DataValidate.class);
            if (annotation != null) {
                String[] requiredParams = annotation.requiredParams();
                if (!ArrayUtils.isEmpty(requiredParams)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < requiredParams.length; i ++) {
                        String params = requiredParams[i];
                        if (!jsonObject.containsKey(params)) {
                            if (i == 0) {
                                stringBuilder.append(params);
                            } else {
                                stringBuilder.append(","+params);
                            }
                        }
                    }

                    if (!"".equals(stringBuilder.toString())) {
                        logger.info("缺少必传参数: " + stringBuilder.toString());
                        String msg = new String("缺少必传参数："+stringBuilder.toString());
                        JSONObject responseJson = new JSONObject();
                        responseJson.put(BootConstants.CODE_KEY, StatusCode.ERROR_REQUIREDPARAMS_LOST);
                        responseJson.put(BootConstants.MESSAGE_KEY, msg);
                        String responseStr = AESUtil.encrypt(responseJson.toString(), BootConstants.AES_KEY, BootConstants.AES_IV);
                        response.getWriter().write(responseStr);
                        return false;
                    }
                }
            }
        }

        request.setAttribute(BootConstants.REQUESTDATA, jsonObject);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("在请求处理后进行调用");
        JSONObject responseJson = (JSONObject) request.getAttribute(BootConstants.REQUESTAFTERDATA);
        if (responseJson.isEmpty()) {
            responseJson = new JSONObject();
            responseJson.put(BootConstants.CODE_KEY, StatusCode.ERROR_DATA_EMPTY);
            responseJson.put(BootConstants.MESSAGE_KEY, "数据为空");
        }
        String responseStr = AESUtil.encrypt(responseJson.toString(), BootConstants.AES_KEY, BootConstants.AES_IV);
        response.getWriter().write(responseStr);
        super.afterCompletion(request, response, handler, ex);
    }
}
