package com.dodonew.handler;

import com.alibaba.fastjson.JSONObject;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.security.AESUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Bruce on 2017/10/16.
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private HttpServletRequest request;

    public RequestHandler(HttpServletRequest request) {
        this.request = request;
    }

    public JSONObject getJSON() {
        String method = request.getMethod();
        Boolean isGet = "GET".equals(method) || "DELETE".equals(method);
        JSONObject jsonObject = null;
        if (isGet) {
            jsonObject = getGETJSONObject();
        } else {
            jsonObject = getPOSTJSONObject();
        }
        return jsonObject;
    }

    private JSONObject getPOSTJSONObject() {
        String inputStreamStr = readStream();
        return getJSONObject(inputStreamStr);
    }

    private JSONObject getGETJSONObject() {
        JSONObject desJson = null;
        String queryString = request.getQueryString();
        if (StringUtils.isEmpty(queryString)) {
            String encryptStr = request.getParameter("Encrypt");
            // 以params方式传递过来的参数，就不需要进行URLDecoder了。
            String desStr = AESUtil.decrypt(encryptStr, BootConstants.AES_KEY, BootConstants.AES_IV);
            desJson = JSONObject.parseObject(desStr);
        } else {
            String[] queryArray = queryString.split("=");
            if (queryArray.length == 2) {
                String encryptStr = queryArray[1];
                // app端GET传输的话，会自动进行URLEncoder编码。所以在后端测试的时候，也要添加一次编码。
                try {
                    encryptStr = URLDecoder.decode(encryptStr, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String desStr = AESUtil.decrypt(encryptStr, BootConstants.AES_KEY, BootConstants.AES_IV);
                desJson = JSONObject.parseObject(desStr);
            }
        }
        return desJson;
    }

    private JSONObject getJSONObject(String data) {
        JSONObject json = new JSONObject();
        JSONObject desJson = new JSONObject();
        String desStr = null;
        if (data.contains("Encrypt")) {
            // 针对非parameters的处理
            json = JSONObject.parseObject(data);
            desStr = AESUtil.decrypt(json.getString("Encrypt"), BootConstants.AES_KEY, BootConstants.AES_IV);
        } else {
            // 针对parameters的处理
            desStr = AESUtil.decrypt(data, BootConstants.AES_KEY, BootConstants.AES_IV);
        }
        desJson = JSONObject.parseObject(desStr);
        return desJson;
    }

    private String readStream() {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            System.out.println("inputStream ==== "+request.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            //System.out.println("readLine === "+bufferedReader.readLine());
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            if (StringUtils.isEmpty(stringBuffer.toString())) {
                String encryptStr = request.getParameter("Encrypt");
                stringBuffer.append(encryptStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
}
