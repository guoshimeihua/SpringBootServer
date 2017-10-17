package com.dodonew.util.security;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by Bruce on 2017/10/14.
 */
public class MD5Util {
    private static byte[] hexBase = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    public static String fileHash(String filePath) {
        return fileHash(new File(filePath));
    }

    /**
     * 用DigestInputStream来计算大文件的md5，也避免内存吃不消。
     */
    public static String fileHash(File file) {
        if (file == null) {
            return "";
        }

        try {
            int bufferSize = 1024 * 1024;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            DigestInputStream dis = new DigestInputStream(fis, messageDigest);
            byte[] buffer = new byte[bufferSize];
            while (dis.read(buffer) > 0) ;
            messageDigest = dis.getMessageDigest();
            byte[] result = messageDigest.digest();
            return byteArrayToHex(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String stringMD5(String string) {
        return stringMD5(string, "utf-8");
    }

    public static String stringMD5(String string, String charsetName) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        try {
            byte[] data = string.getBytes(charsetName);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data);
            byte[] result = messageDigest.digest();
            return byteArrayToHex(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * md5对字符串加密
     * 方法一：使用hexBase 48, 49, 50, 51
     * 方法二：hexDigts 0 1 2 3 4 5
     * 这两种方法得到的md5加密结果是一样的。
     * 实现字节数组到十六进制的转换
     */
    public static String byteArrayToHex(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return "";
        }

        StringBuffer stringBuffer = new StringBuffer();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append((char) hexBase[((bytes[i] & 0xF0) >> 4)]);
            stringBuffer.append((char) hexBase[(bytes[i] & 0xF)]);
        }

        return stringBuffer.toString();
    }

    public static String byteArrayToHex2(byte[] byteArray) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>>4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray).toLowerCase();
    }

    public static String createMD5Sign(SortedMap signMap, String key) {
        StringBuffer stringBuffer = new StringBuffer();
        Set<Map.Entry<String, String>> paramSet = signMap.entrySet();
        for (Map.Entry<String, String> entry : paramSet) {
            String k = entry.getKey();
            if (k.equals("sign") || k.equals("mysign") || k.equals("code")) {
                continue;
            }
            String v = entry.getValue();
            stringBuffer.append(k + "=" + v + "&");
        }
        String params = stringBuffer.append("key=" + key).toString();
        return stringMD5(params, "utf-8").toUpperCase();
    }
}
