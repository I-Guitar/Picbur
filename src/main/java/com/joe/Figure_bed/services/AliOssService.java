package com.joe.Figure_bed.services;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Create by joe on 2019/6/12
 */
@Service
public class AliOssService {

    @Value("endpoint")
    private String endpoint;

    @Value("accessKeyId")
    private String accessKeyId;

    @Value("secretAccessKey")
    private String secretAccessKey;

    private final OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);

    public void putObject(String bucketName, String objectName, InputStream inputStream) {
        PutObjectResult result = ossClient.putObject(bucketName, objectName, inputStream);
    }
}
