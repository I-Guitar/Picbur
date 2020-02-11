package com.joe.picBed.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

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

    /**
     * 获取resource绝对路径
     */
    public static String getResourcePath() {
        return Tools.class.getClassLoader().getResource("").getPath();
    }

    /**
     * 读取properties文件内容，以properties对象返回
     */
    public static Properties readFileForProp(String path) throws IOException {
        final Properties prop = new Properties();
        try (
                final FileInputStream fileInputStream = new FileInputStream(path)
        ) {
            prop.load(fileInputStream);
        }
        return prop;
    }

    /**
     * 对象序列化
     */
    public static void serializeObj(Serializable obj, String path) throws IOException {
        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(path);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(obj);
        }
    }

    /**
     * 对象反序列化
     */
    public static <T> T deserializeObj(String path) throws IOException, ClassNotFoundException {
        try (
                final FileInputStream fileInputStream = new FileInputStream(path);
                final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (T) objectInputStream.readObject();
        }
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
