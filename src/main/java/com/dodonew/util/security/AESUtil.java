package com.dodonew.util.security;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bruce on 2017/10/15.
 */
public class AESUtil {
    public static String encrypt(String data, String key, String iv) {
        return encrypt(data, key, iv, "utf-8");
    }

    public static String encrypt(String data, String key, String iv, String charsetName) {
        try {
            return encrypt(data.getBytes(charsetName), key.getBytes(charsetName), iv.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encrypt(byte[] data, byte[] key, byte[] iv) {
        // 这里的key为了与iOS统一，不可以使用KeyGenerator、SecureRandom、SecretKey生成
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] result = cipher.doFinal(data);
            // 对加密后的数据，尽心base64编码
            return Base64Util.encode(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(String data, String key, String iv){
        return decrypt(data, key, iv, "utf-8");
    }

    public static String decrypt(String data, String key, String iv, String charsetName) {
        try {
            byte[] contentData = Base64Util.decodeBytes(data.getBytes(charsetName));
            return decrypt(contentData, key.getBytes(charsetName), iv.getBytes(charsetName), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(byte[] data, byte[] key, byte[] iv, String charsetName) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] result = cipher.doFinal(data);
            return new String(result, charsetName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
