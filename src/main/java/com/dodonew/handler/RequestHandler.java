package com.dodonew.handler;

import com.alibaba.fastjson.JSONObject;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.security.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

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
        System.out.println("method GET ==== " + RequestMethod.GET);
        System.out.println("method POST ==== " + RequestMethod.DELETE);
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
        String queryString = request.getQueryString();
        String[] queryArray = queryString.split("=");
        JSONObject desJson = null;
        if (queryArray.length == 2) {
            String encryptStr = queryArray[1];
            try {
                encryptStr = URLDecoder.decode(encryptStr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String desStr = AESUtil.decrypt(encryptStr, BootConstants.AES_KEY, BootConstants.AES_IV);
            desJson = JSONObject.parseObject(desStr);
        }
        return desJson;
    }

    private JSONObject getJSONObject(String data) {
        JSONObject json = new JSONObject();
        JSONObject desJson = new JSONObject();
        json = JSONObject.parseObject(data);
        String desStr = AESUtil.decrypt(json.getString("Encrypt"), BootConstants.AES_KEY, BootConstants.AES_IV);
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
