package com.joe.Figure_bed.services;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Create by joe on 2019/6/12
 */
@Service
public class AliOssService {

    @Value("${endpoint}")
    private String endpoint;

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${secretAccessKey}")
    private String secretAccessKey;

    private static final MessageFormat urlFormat = new MessageFormat("http://{0}.{1}/{2}");

    private OSSClient ossClient = null;

    public String putObject(String bucketName, String objectName, InputStream inputStream) {
        if (ossClient == null) {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        }
        ossClient.putObject(bucketName, objectName, inputStream);
        return urlFormat.format(new Object[]{bucketName, endpoint, objectName});
    }
}
