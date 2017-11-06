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
public class UserControllerTests {
    /**
     * 测试GET方法：用户列表、单独一个用户
     */
    @Test
    public void testUserList() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("pageIndex", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("用户列表测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUser() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试POST方法：添加一个用户
     */
    @Test
    public void testAddUser() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("loginName", "苏城");
        sortedMap.put("password", "123456");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写测试的时候要多个角度都测试下，成功的、不成功的、临界点各方面的测试，这样才能保证你程序的健壮性。
     */
    @Test
    public void testAddUserException() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("loginName", "苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底");
        sortedMap.put("password", "123456");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试DELETE方法：删除一个用户
     */
    @Test
    public void testDeleteUser() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "4");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteUserFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "100");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试PUT方法：更新一个用户（需要提供全部信息）
     */
    @Test
    public void testPutUser() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "8");
        sortedMap.put("userName", "苏城");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPutRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PUT用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPutUserFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "24");
        sortedMap.put("userName", "苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底苏格拉底");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPutRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PUT用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试PATCH方法：更新一个用户（只需要提供部分信息即可）但是因为提供的都是全部信息，所以在此时PUT和PATCH的作用是一样的。
     */
    @Test
    public void testPatchUser() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "8");
        sortedMap.put("password", "456789");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPatchUserFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("userId", "8");
        sortedMap.put("password", "333399999000033339999900003333999990000333399999000033339999900003333999990000");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/users/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH用户测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
