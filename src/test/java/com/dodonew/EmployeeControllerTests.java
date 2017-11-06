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
 * Created by Bruce on 2017/11/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTests {
    @Test
    public void testEmployeeList() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("pageIndex", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("员工列表测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmployee() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("employeeId", "9");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees/id")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddEmployee() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("name", "陈祖光");
        sortedMap.put("cardId", "411122199009783456");
        sortedMap.put("education", "硕士");
        sortedMap.put("phone", "18670004468");
        sortedMap.put("deptId", "3");
        sortedMap.put("jobId", "3");
        sortedMap.put("address", "深圳前海湾");
        sortedMap.put("email", "qianhanwan@dodonew.com");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddEmployeeFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("name", "陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光陈祖光");
        sortedMap.put("cardId", "411122199009783456");
        sortedMap.put("education", "硕士");
        sortedMap.put("phone", "18670004468");
        sortedMap.put("deptId", "3");
        sortedMap.put("jobId", "3");
        sortedMap.put("address", "深圳前海湾");
        sortedMap.put("email", "qianhanwan@dodonew.com");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteEmployeeSuccess() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("employeeId", "6");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteEmployeeFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("employeeId", "100");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPatchEmployee() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("employeeId", "5");
        sortedMap.put("hobby", "听陈奕迅的歌曲");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPatchEmployeeFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("employeeId", "5");
        sortedMap.put("hobby", "听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲听陈奕迅的歌曲");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/employees/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH员工测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
