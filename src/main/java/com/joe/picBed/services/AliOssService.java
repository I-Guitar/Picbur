package com.joe.picBed.services;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.joe.picBed.entity.ConfigEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Create by joe on 2019/6/12
 */
@Service
public class AliOssService {
    private static final MessageFormat urlFormat = new MessageFormat("http://{0}.{1}/{2}");

    @Autowired
    private ConfigEntry configEntry;

    private OSSClient ossClient;


    @PostConstruct
    private void init() throws IOException {
        ossClient = new OSSClient(configEntry.getEndpoint(), new DefaultCredentialProvider(configEntry.getAccessKeyId(), configEntry.getSecretAccessKey()), null);
    }

    public String putObject(String bucketName, String objectName, InputStream inputStream) {
        ossClient.putObject(bucketName, objectName, inputStream);
        return urlFormat.format(new Object[]{bucketName, configEntry.getEndpoint(), objectName});
    }
}
