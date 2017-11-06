package com.dodonew.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.security.AESUtil;
import com.dodonew.util.security.MD5Util;

import java.util.Map;
import java.util.SortedMap;

/**
 * Created by Bruce on 2017/10/30.
 */
public class EncryptUtils {
    public static final String getEncryptStr(SortedMap<String, String> sortedMap) {
        sortedMap.put("timeStamp", System.currentTimeMillis()+"");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String sign = MD5Util.createMD5Sign(sortedMap, BootConstants.SIGN_KEY);
        System.out.println("sign : " + sign);
        sortedMap.put("sign", sign);

        String mapStr = JSON.toJSONString(sortedMap, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        String encryptStr = AESUtil.encrypt(mapStr, BootConstants.AES_KEY, BootConstants.AES_IV);
        return encryptStr;
    }

    public static final String getDecryptStr(String encryptStr) {
        String decryptStr = AESUtil.decrypt(encryptStr, BootConstants.AES_KEY, BootConstants.AES_IV);
        return decryptStr;
    }
}
