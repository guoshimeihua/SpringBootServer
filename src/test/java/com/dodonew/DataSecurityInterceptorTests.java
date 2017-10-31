package com.dodonew;

import com.alibaba.fastjson.JSON;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.security.AESUtil;
import com.dodonew.util.security.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Bruce on 2017/10/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataSecurityInterceptorTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTimeValidate() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        //sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        sortedMap.put("timeStamp", "1509416666000");
        sortedMap.put("deptId", "1");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hrm/api/depts/id")
                    .param("Encrypt", encryptStr)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            System.out.println("content = " + content);
            String decryptStr = AESUtil.decrypt(content, BootConstants.AES_KEY, BootConstants.AES_IV);
            System.out.println("解密后的字符串: " + decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSignValidate() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        sortedMap.put("deptId", "1");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, "sdlkjsdljf0j2fsj");
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hrm/api/depts/id")
                    .param("Encrypt", encryptStr)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            System.out.println("content = " + content);
            String decryptStr = AESUtil.decrypt(content, BootConstants.AES_KEY, BootConstants.AES_IV);
            System.out.println("解密后的字符串: " + decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRequiredParams() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hrm/api/depts/id")
                    .param("Encrypt", encryptStr)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            System.out.println("content = " + content);
            String decryptStr = AESUtil.decrypt(content, BootConstants.AES_KEY, BootConstants.AES_IV);
            System.out.println("解密后的字符串: " + decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
