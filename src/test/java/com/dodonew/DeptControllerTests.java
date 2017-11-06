package com.dodonew;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.domain.Dept;
import com.dodonew.service.DeptService;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.security.AESUtil;
import com.dodonew.util.security.MD5Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Bruce on 2017/10/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeptControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeptService deptService;

    /**
     * 添加拦截器的支持
     */
    //@Before
    //public void setUp() throws Exception {
    //    mockMvc = MockMvcBuilders.standaloneSetup(new DeptController())
    //            .addInterceptors(new DataSecurityInterceptor()).build();
    //}

    @Before
    public void setUp() throws Exception {
        Dept dept = new Dept();
        dept.setId(1);
        dept.setDepartName("研发部");
        dept.setRemark("研发部");
        // given的主要作用就是对controller下面的接口进行下快速验证而已，并不会发所有的数据全部返回给你的。
        BDDMockito.given(deptService.findDept(1)).willReturn(dept);
        BDDMockito.given(deptService.findDeptList(1, 10)).willReturn(Arrays.asList(dept));
    }

    /**
     * standaloneSetup面向的是单元测试，
     * webAppContextSetup面向的是集成测试。
     *
     * 现在测试DeptController，因为涉及到service，所以要使用集成测试才可以的。
     */
    @Test
    public void testDept() throws Exception {
        // 在这里要把加密好的参数给模拟出来，填充到这里就好了。
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        sortedMap.put("deptId", "1");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);

        // 直接在URL后面拼接和以param传递的方式，两种都给添加上去了。
        //MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hrm/api/depts/id?Encrypt={encrypt}", encryptStr)
        //        .accept(MediaType.APPLICATION_JSON))
        //        .andExpect(MockMvcResultMatchers.status().isOk())
        //        .andDo(MockMvcResultHandlers.print())
        //        .andReturn();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hrm/api/depts/id")
                .param("Encrypt", encryptStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("content = " + content);
        String decryptStr = AESUtil.decrypt(content, BootConstants.AES_KEY, BootConstants.AES_IV);
        JSONObject resultJson = JSONObject.parseObject(decryptStr);
        System.out.println("单个部门测试信息 : " + resultJson);
    }

    @Test
    public void testDeptList() throws Exception {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        sortedMap.put("pageIndex", "1");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hrm/api/depts")
                .param("Encrypt", encryptStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("content = " + content);
        String decryptStr = AESUtil.decrypt(content, BootConstants.AES_KEY, BootConstants.AES_IV);
        JSONObject resultJson = JSONObject.parseObject(decryptStr);
        System.out.println("部门列表测试信息 : " + resultJson);
    }
}
