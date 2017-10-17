package com.dodonew.util.security;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by Bruce on 2017/10/15.
 */
public class Base64Util {
    public static String encode(String data) {
        return encode(data, "utf-8");
    }

    public static String encode(String data, String charsetName) {
        try {
            return encode(data.getBytes(charsetName), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encode(byte[] data) {
        return encode(data, "utf-8");
    }

    public static String encode(byte[] data, String charsetName) {
        try {
            byte[] b = Base64.getEncoder().encode(data);
            return new String(b, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] encodeBytes(byte[] data) {
        byte[] b = Base64.getEncoder().encode(data);
        return b;
    }

    public static String decode(String data) {
        return decode(data, "utf-8");
    }

    public static String decode(String data, String charsetName) {
        try {
            return decode(data.getBytes(charsetName), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decode(byte[] data) {
        return decode(data, "utf-8");
    }

    public static String decode(byte[] data, String charsetName) {
        try {
            byte[] b = Base64.getDecoder().decode(data);
            return new String(b, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] decodeBytes(byte[] data) {
        byte[] b = Base64.getDecoder().decode(data);
        return b;
    }
}
