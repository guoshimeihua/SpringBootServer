package com.dodonew;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.domain.Document;
import com.dodonew.domain.User;
import com.dodonew.service.DocumentService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

/**
 * Created by Bruce on 2017/11/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UploadFileTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocumentService documentService;

    @Before
    public void setUp() {
        Document document = new Document();
        document.setTitle("高圆圆");
        document.setRemark("大美女一枚");
        User user = new User();
        user.setId(1);
        document.setUser(user);
        document.setFileName("userAvatar.jpeg");
        BDDMockito.given(documentService.addDocument(document)).willReturn(true);
    }

    @Test
    public void testUploadTest() {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        sortedMap.put("ownerId", "1");
        sortedMap.put("title", "刘亦菲");
        sortedMap.put("remark", "古典美女");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("hrm2.jpg");
        try {
            MockMultipartFile multipartFile = new MockMultipartFile("file", "userAvatar5", "image/jpeg", inputStream);
            mockMvc.perform(fileUpload("/hrm/api/documents").file(multipartFile).param("Encrypt", encryptStr))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
