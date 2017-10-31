package com.dodonew;

import com.alibaba.fastjson.JSONObject;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.http.EncryptUtils;
import com.dodonew.util.http.HttpUtils;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Bruce on 2017/10/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptHttpClientTests {
    /**
     * 测试GET方法：部门列表、单独一个部门
     */
    @Test
    public void testDeptList() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("pageIndex", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/depts")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("部门列表测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDept() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("deptId", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/depts/id")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("部门测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试POST方法：添加一个部门
     */
    @Test
    public void testAddDept() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("departName", "总经办");
        sortedMap.put("remark", "总经办制定公司发展的未来规划");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/depts");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加部门测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试DELETE方法：删除一个部门
     */
    @Test
    public void testDeleteDept() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("deptId", "23");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/depts/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除部门测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试PUT方法：更新一个部门（需要提供全部信息）
     */
    @Test
    public void testPutDept() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("deptId", "24");
        sortedMap.put("remark", "负责公司的战略规划");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/depts/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPutRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PUT部门测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试PATCH方法：更新一个部门（只需要提供部门信息即可）但是因为提供的都是全部信息，所以在此时PUT和PATCH的作用是一样的。
     */
    @Test
    public void testPatchDept() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("deptId", "24");
        sortedMap.put("departName", "战略事业部");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/depts/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH部门测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
