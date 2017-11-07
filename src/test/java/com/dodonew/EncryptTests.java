package com.dodonew;

import com.dodonew.util.common.BootConstants;
import com.dodonew.util.security.AESUtil;
import com.dodonew.util.security.MD5Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import java.io.File;

/**
 * Created by Bruce on 2017/11/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptTests {
    @Test
    public void testBase64() {
        String str = "123456";
        String encodeStr = Base64Utils.encodeToString(str.getBytes());
        System.out.println("base64 encode str : " + encodeStr);
        String decodeStr = new String(Base64Utils.decodeFromString(encodeStr));
        System.out.println("base64 decode str : " + decodeStr);
        Assert.assertEquals(str, decodeStr);
    }

    @Test
    public void testMD5Str() {
        String str = "456789123456";
        String md5Str = MD5Util.stringMD5(str);
        System.out.println("md5 str : " + md5Str);
        Assert.assertTrue(md5Str.length() == 32);
    }

    @Test
    public void testMD5File() {
        File file = new File("/Users/Bruce/Desktop/hrm4.jpg");
        String md5File = MD5Util.fileHash(file);
        System.out.println("md5 file : " + md5File);
        Assert.assertTrue(md5File.length() == 32);
    }

    @Test
    public void testAES() {
        String str = "0987654f321";
        String encryptStr = AESUtil.encrypt(str, BootConstants.AES_KEY, BootConstants.AES_IV);
        System.out.println("encryptStr : " + encryptStr);
        String decryptStr = AESUtil.decrypt(encryptStr, BootConstants.AES_KEY, BootConstants.AES_IV);
        System.out.println("decryptStr : " + decryptStr);
        Assert.assertEquals(str, decryptStr);
    }
}
