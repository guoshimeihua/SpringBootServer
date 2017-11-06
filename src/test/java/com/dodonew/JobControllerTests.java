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
public class JobControllerTests {
    /**
     * 测试GET方法：职位列表、单独一个职位
     */
    @Test
    public void testJobList() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("pageIndex", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("职位列表测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJob() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobId", "2");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs/id")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("职位测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试POST方法：添加一个职位
     */
    @Test
    public void testAddJob() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobName", "产品经理");
        sortedMap.put("remark", "产品经理");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加职位测试信息 : " + resultJson);
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
    public void testAddJobException() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobName", "产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理产品经理");
        sortedMap.put("remark", "产品经理");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加职位测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试DELETE方法：删除一个职位
     */
    @Test
    public void testDeleteJob() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobId", "10");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除职位测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteJobFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobId", "100");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除职位测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试PATCH方法：更新一个职位（只需要提供部门信息即可）但是因为提供的都是全部信息，所以在此时PUT和PATCH的作用是一样的。
     */
    @Test
    public void testPatchJob() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobId", "4");
        sortedMap.put("jobName", "产品经理");
        sortedMap.put("remark", "产品经理");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH职位测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPatchJobFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jobId", "4");
        sortedMap.put("jobName", "战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部战略事业部");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/jobs/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH职位测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
