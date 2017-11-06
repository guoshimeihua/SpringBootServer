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
 * Created by Bruce on 2017/11/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeControllerTests {
    @Test
    public void testNoticeList() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("pageIndex", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("公告列表测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNotice() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("noticeId", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices/id")
                    .addParameter("Encrypt", encryptStr);
            String content = HttpUtils.sendGetRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddNotice() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("title", "习近平会见俄罗斯总理");
        sortedMap.put("content", "习近平欢迎梅德韦杰夫在中共十九大胜利闭幕后随即访华，强调俄罗斯是中国的最大邻国和全面战略协作伙伴，中方发展和深化中俄关系的明确目标和坚定决心不会改变。中方愿同俄方一道，扩大各领域、全方位合作，密切在国际事务中的协调和配合，推动构建人类命运共同体。相信中俄全面战略协作伙伴关系会在新起点上把握新机遇，展现新气象，取得新成果。");
        sortedMap.put("userId", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("title", "习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理习近平会见俄罗斯总理");
        sortedMap.put("content", "习近平欢迎梅德韦杰夫在中共十九大胜利闭幕后随即访华，强调俄罗斯是中国的最大邻国和全面战略协作伙伴，中方发展和深化中俄关系的明确目标和坚定决心不会改变。中方愿同俄方一道，扩大各领域、全方位合作，密切在国际事务中的协调和配合，推动构建人类命运共同体。相信中俄全面战略协作伙伴关系会在新起点上把握新机遇，展现新气象，取得新成果。");
        sortedMap.put("userId", "1");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPostRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("添加通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteNoticeSuccess() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("noticeId", "7");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteNoticeFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("noticeId", "100");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices/id").addParameter("Encrypt", encryptStr);
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendDeleteRequest(builder.build());
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("删除通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPatchNotice() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("noticeId", "8");
        sortedMap.put("title", "习近平会见俄罗斯总统普京");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPatchNoticeFailure() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("noticeId", "8");
        sortedMap.put("title", "习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京习近平会见俄罗斯总统普京");
        String encryptStr = EncryptUtils.getEncryptStr(sortedMap);
        try {
            URIBuilder builder = new URIBuilder(BootConstants.LOCAL_HOST+"/hrm/api/notices/id");
            Map<String, String> params = new HashMap<>();
            params.put("Encrypt", encryptStr);
            String content = HttpUtils.sendPatchRequest(builder.build(), params);
            System.out.println("content = " + content);
            String decryptStr = EncryptUtils.getDecryptStr(content);
            JSONObject resultJson = JSONObject.parseObject(decryptStr);
            System.out.println("更新PATCH通知测试信息 : " + resultJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
