package com.joe.picBed.utils.server;

import com.joe.picBed.entity.exceptions.MinioInitializeException;
import com.joe.picBed.entity.exceptions.MinioPutObjectException;
import io.minio.MinioClient;

import java.io.InputStream;

/**
 * Created by joe on 2020/3/20
 * <p>
 * MinIOClient
 */
public class MyMinioClient {

    private final MinioClient client;

    public MyMinioClient(final String endPoint, final String accessKey, final String secretAccesskey) throws MinioInitializeException {
        try {
            client = new MinioClient(endPoint, accessKey, secretAccesskey);
        } catch (Exception e) {
            throw new MinioInitializeException(e);
        }
    }

    public void putObject(String bucketName, String objectName, InputStream inputStream, String contentType) throws MinioPutObjectException {
        try {
            client.putObject(bucketName, objectName, inputStream, null, null, null, contentType);
        } catch (Exception e) {
            throw new MinioPutObjectException(e);
        }
    }

    public void putImg(String bucketName, String objectName, InputStream inputStream) throws MinioPutObjectException {
        putObject(bucketName, objectName, inputStream, "image/png");
    }

}
