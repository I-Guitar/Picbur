package com.joe.picBed.utils;

import com.joe.picBed.entity.enums.ContentType;
import com.joe.picBed.entity.enums.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by joe on 19-6-16
 */
public class Tools {
    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    private static final Map<String, String> STREAM_TYPE_MAPPER = new HashMap<>();

    private static final Map<String, String> CONTENT_TYPE_MAPPER = new HashMap<>();

    static {
        for (FileType fileType : FileType.values()) {
            final String typeName = fileType.typeName;

            for (String code : fileType.streamCodes) {
                STREAM_TYPE_MAPPER.put(code, typeName);
            }
        }

        for (ContentType contentType : ContentType.values()) {
            CONTENT_TYPE_MAPPER.put(contentType.fileType, contentType.contentType);
        }
    }

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
     * 预测文件扩展名
     */
    public static String predictExtName(String filePath) {
        int index = filePath.lastIndexOf(".");
        if (index > 0) {
            return filePath.substring(index + 1);
        }
        return "";
    }

    /**
     * 预测文件扩展名
     * <p>
     * * 预测过后的 input stream 不可用
     */
    public static String predictExtName(InputStream inputStream) {
        try {
            byte[] bytes = new byte[5];
            if (inputStream.read(bytes, 0, bytes.length) > -1) {
                String fileCode = bytesToHexString(bytes);

                if (fileCode != null && fileCode.length() == 10) {
                    return STREAM_TYPE_MAPPER.get(fileCode.toLowerCase());
                }
            }
        } catch (Exception e) {
            logger.warn("Can't predict extract name from input stream!", e);
        }
        return "";
    }

    /**
     * 预测 http Content-Type
     * <p>
     * * 读取过后的 input stream 不可用
     */
    public static String predictContentType(InputStream inputStream) {
        final String extName = predictExtName(inputStream);
        if (StringUtils.isEmpty(extName)) {
            return ContentType.DEFAULT.contentType;
        }
        return predictContentType(extName);
    }

    /**
     * 预测 http Content-Type
     */
    public static String predictContentType(String extName) {
        final String contentType = CONTENT_TYPE_MAPPER.get(extName);
        return contentType == null ? ContentType.DEFAULT.contentType : contentType;
    }

    /**
     * 获取resource绝对路径
     */
    public static String getResourcePath() {
        return Objects.requireNonNull(Tools.class.getClassLoader().getResource("")).getPath();
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

    public static String getImageExtName(String fileName) {
        String extraName = predictExtName(fileName);
        return extraName.length() > 0 ? extraName : "png";
    }

    private static byte[] md5ToBytes(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(bytes);
        return md5.digest();
    }

    /**
     * 字节数组转字符串
     */
    private static String bytesToHexString(byte[] src) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (null == src || src.length < 1) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
