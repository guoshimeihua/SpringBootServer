package com.dodonew.util.security;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Bruce on 2017/10/15.
 */
public class DESUtil {
    public static String encrypt(String data, String key, String iv) {
        return encrypt(data, "utf-8", key, iv);
    }

    public static String encrypt(String data, String charsetName, String key, String iv) {
        try {
            return encrypt(data.getBytes(charsetName), key.getBytes(charsetName), iv.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encrypt(byte[] data, byte[] key, byte[] iv) {
        // 对key进行MD5加密，这样才和iOS中的一样
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key);
            DESKeySpec dks = new DESKeySpec(messageDigest.digest());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec resultIv = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, resultIv);

            return Base64Util.encode(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(String data, String key, String iv) {
        return decrypt(data, "utf-8", key, iv);
    }

    public static String decrypt(String data, String charsetName, String key, String iv) {
        try {
            return decrypt(data.getBytes(charsetName), key.getBytes(charsetName), iv.getBytes(charsetName), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(byte[] data, byte[] key, byte[] iv, String charsetName) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key);
            DESKeySpec dks = new DESKeySpec(messageDigest.digest());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec resultIv = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, resultIv);
            byte[] result = cipher.doFinal(Base64Util.decodeBytes(data));
            return new String(result, charsetName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
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
