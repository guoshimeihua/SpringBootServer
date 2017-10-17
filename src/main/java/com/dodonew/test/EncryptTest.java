package com.dodonew.test;

import com.dodonew.util.security.AESUtil;
import com.dodonew.util.security.DESUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Bruce on 2017/10/14.
 */
public class EncryptTest {
    public static void main(String[] args) {
        System.out.println("加密测试");
        //base64Test();
        //desTest();
        aesTest();
    }

    public static void md5Test() {
        //String test = "123456";
        //String md5 = MD5Util.stringMD5(test);
        //System.out.println(md5);

        //String filePath = "/Users/Bruce/Desktop/shake_end.mp3";
        //String fileMD5 = MD5Util.fileHash(filePath);
        //System.out.println(fileMD5);
    }

    public static void base64Test() {
        //String test = "123456";
        //String encodeStr = Base64Utils.encodeToString(test.getBytes());
        //System.out.println("encodeStr ====== "+encodeStr);
        //
        //byte[] decodeByteArray = Base64Utils.decodeFromString(encodeStr);
        //String decodeStr = new String(decodeByteArray);
        //System.out.println("decodeStr ====== "+decodeStr);
        //
        //String encodeUrlStr = Base64Utils.encodeToUrlSafeString(test.getBytes());
        //System.out.println("encodeUrlStr ====== "+encodeUrlStr);
        //
        //byte[] decodeByteUrlArray = Base64Utils.decodeFromUrlSafeString(encodeUrlStr);
        //String decodeUrlStr = new String(decodeByteUrlArray);
        //System.out.println("decodeUrlStr ===== "+decodeUrlStr);

        //String test = "123456";
        //String encodeStr = Base64Util.encode(test);
        //System.out.println("encodeStr ====== "+encodeStr);
        //
        //String decodeStr = Base64Util.decode(encodeStr);
        //System.out.println("decodeStr ====== "+decodeStr);

        String test = "123456++++=====";
        try {
            String urlEncodeStr = URLEncoder.encode(test, "utf-8");
            System.out.println("urlEncodeStr ===== "+urlEncodeStr);
            String urlDecodeStr = URLDecoder.decode(urlEncodeStr, "utf-8");
            System.out.println("urlDecodeStr ===== "+urlDecodeStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void desTest() {
        String data = "123456";
        String key = "65102933";
        String iv = "32028092";

        String desStr = DESUtil.encrypt(data, key, iv);
        System.out.println("desStr ===== "+desStr);

        String desDecryptStr = DESUtil.decrypt(desStr, key, iv);
        System.out.println("decryptStr ====== "+desDecryptStr);
    }

    public static void aesTest() {
        String data = "123456";
        String key = "!@*%$65102933ddn";
        String iv = "!@*%$32028092ddn";

        String aesStr = AESUtil.encrypt(data, key, iv);
        System.out.println("aes 加密 ===== "+aesStr);

        String decryptStr = AESUtil.decrypt(aesStr, key, iv);
        System.out.println("aes 解密 ===== "+decryptStr);
    }
}
