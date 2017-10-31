package com.dodonew.util.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/10/30.
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String sendGetRequest(URI uri) {
        return sendRequest(uri, "GET");
    }

    public static String sendDeleteRequest(URI uri) {
        return sendRequest(uri, "DELETE");
    }

    private static String sendRequest(URI uri, String methodType) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.info("URL : " + uri);
        // 创建对应的http请求
        HttpRequestBase requestBase = null;
        if ("GET".equals(methodType)) {
            requestBase = new HttpGet(uri);
        } else if ("DELETE".equals(methodType)) {
            requestBase = new HttpDelete(uri);
        }
        try {
            // 执行http请求
            CloseableHttpResponse httpResponse = httpClient.execute(requestBase);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                Header[] headers = httpResponse.getAllHeaders();
                for (Header header : headers) {
                    logger.info("header name : " + header.getName() + "heaer value : " + header.getValue());
                }
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
                }
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    result = IOUtils.toString(inputStream, "utf-8");
                    // 把httpEntity给关闭掉
                    EntityUtils.consume(httpEntity);
                } else {
                    throw new ClientProtocolException("Response contains no content");
                }
            } finally {
                httpResponse.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("请求返回的数据 ===== " + result);
        return result;
    }

    public static String sendPostRequest(URI uri, Map<String, String> params) {
        return sendFormRequest(uri, params, "POST");
    }

    public static String sendPutRequest(URI uri, Map<String, String> params) {
        return sendFormRequest(uri, params, "PUT");
    }

    public static String sendPatchRequest(URI uri, Map<String, String> params) {
        return sendFormRequest(uri, params, "PATCH");
    }

    private static String sendFormRequest(URI uri, Map<String, String> params, String methodType) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.info("URL : " + uri);
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, "utf-8");
            CloseableHttpResponse httpResponse = null;
            if ("POST".equals(methodType)) {
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(entity);
                httpResponse = httpClient.execute(httpPost);
            } else if ("PUT".equals(methodType)) {
                HttpPut httpPut = new HttpPut(uri);
                httpPut.setEntity(entity);
                httpResponse = httpClient.execute(httpPut);
            } else if ("PATCH".equals(methodType)) {
                HttpPatch httpPatch = new HttpPatch(uri);
                httpPatch.setEntity(entity);
                httpResponse = httpClient.execute(httpPatch);
            }

            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                Header[] headers = httpResponse.getAllHeaders();
                for (Header header : headers) {
                    logger.info("header name : " + header.getName() + "heaer value : " + header.getValue());
                }
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
                }
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    result = IOUtils.toString(inputStream, "utf-8");
                    // 把httpEntity给关闭掉
                    EntityUtils.consume(httpEntity);
                } else {
                    throw new ClientProtocolException("Response contains no content");
                }
            } finally {
                httpResponse.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("请求返回的数据 ===== " + result);
        return result;
    }
}
