package com.joe.Figure_bed.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by joe on 19-6-16
 */
public class Tools {
    /**
     * 字节数组做md5加密
     */
    public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        for (byte b : md5ToBytes(bytes)) {
            sb.append(String.format("%02X", b));  // 10进制字符转16进制
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 获取文件扩展名
     */
    public static String getExtraName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return "";
    }

    public static String getImageExtraName(String fileName) {
        String extraName = getExtraName(fileName);
        return extraName.length() > 0 ? extraName : "jpg";
    }

    private static byte[] md5ToBytes(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(bytes);
        return md5.digest();
    }
}
