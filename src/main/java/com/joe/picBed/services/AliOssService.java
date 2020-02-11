package com.joe.picBed.services;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.joe.picBed.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

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

    private OSSClient ossClient;

    @PostConstruct
    private void init() throws IOException {
        // accessKey校验
        if (StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey)) {
            final Properties prop = Tools.readFileForProp("conf.properties");
            endpoint = prop.getProperty("endpoint");
            accessKeyId = prop.getProperty("accessKeyId");
            secretAccessKey = prop.getProperty("secretAccessKey");

            if (StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey)) {
                throw new RuntimeException("The configuration file must have full parameters, Please check the 'conf.properties'");
            }
        }
        ossClient = new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, secretAccessKey), null);
    }

    public String putObject(String bucketName, String objectName, InputStream inputStream) {
        ossClient.putObject(bucketName, objectName, inputStream);
        return urlFormat.format(new Object[]{bucketName, endpoint, objectName});
    }
}
