package com.joe.picBed.services.impl;

import com.joe.picBed.entity.exceptions.MinioInitializeException;
import com.joe.picBed.entity.exceptions.MinioPutObjectException;
import com.joe.picBed.services.ImageStoreService;
import com.joe.picBed.utils.Tools;
import com.joe.picBed.utils.server.MyMinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by joe on 2020/3/20
 */
@Service
public class ImageStoreServiceImpl implements ImageStoreService {

    @Value("${endpoint}")
    private String endpoint;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${secretAccessKey}")
    private String secretAccessKey;

    private MyMinioClient minIOClient;

    private static final MessageFormat URL_FORMAT = new MessageFormat("{0}/{1}/{2}");

    @Override
    public String putImg(String objectName, InputStream inputStream) throws MinioPutObjectException, IOException {
        if (minIOClient == null) {
            clientInit();
        }
        minIOClient.putImg(bucketName, objectName, inputStream);
        return URL_FORMAT.format(new Object[]{endpoint, bucketName, objectName});
    }

    private void clientInit() throws IOException {
        // accessKey校验
        if (StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey) || StringUtils.isEmpty(bucketName)) {
            final Properties prop = Tools.readFileForProp("conf.properties");
            endpoint = prop.getProperty("endpoint");
            accessKeyId = prop.getProperty("accessKeyId");
            secretAccessKey = prop.getProperty("secretAccessKey");
            bucketName = prop.getProperty("bucketName");

            if (StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey)) {
                throw new RuntimeException("The configuration file must have full parameters, Please check the 'conf.properties'");
            }
        }
        try {
            minIOClient = new MyMinioClient(endpoint, accessKeyId, secretAccessKey);
        } catch (MinioInitializeException e) {
            throw new RuntimeException(e);
        }
    }
}
