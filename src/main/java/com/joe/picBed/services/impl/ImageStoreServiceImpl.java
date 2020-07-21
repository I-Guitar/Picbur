package com.joe.picBed.services.impl;

import com.hujinwen.utils.FileUtils;
import com.hujinwen.utils.RandomUtils;
import com.joe.picBed.entity.MinIONode;
import com.joe.picBed.entity.exceptions.MinioInitializeException;
import com.joe.picBed.entity.exceptions.MinioPutObjectException;
import com.joe.picBed.services.ImageStoreService;
import com.joe.picBed.utils.server.MinIOCluster;
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

    @Value("${endpoints}")
    private String endpoint;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${secretAccessKey}")
    private String secretAccessKey;

    private MinIOCluster minIOCluster;

    private String[] endpoints;

    private static final MessageFormat URL_FORMAT = new MessageFormat("{0}/{1}/{2}");

    @Override
    public String putImg(String objectName, InputStream inputStream) throws MinioPutObjectException, IOException {
        if (minIOCluster == null) {
            clientInit();
        }
        minIOCluster.putImg(bucketName, objectName, inputStream);
        return URL_FORMAT.format(new Object[]{RandomUtils.randomChoice(endpoints), bucketName, objectName});
    }

    private void clientInit() throws IOException {
        // accessKey校验
        if (StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey) || StringUtils.isEmpty(bucketName)) {
            final Properties prop = FileUtils.readProperties("conf.properties");
            endpoint = prop.getProperty("endpoints");
            accessKeyId = prop.getProperty("accessKeyId");
            secretAccessKey = prop.getProperty("secretAccessKey");
            bucketName = prop.getProperty("bucketName");

            if (StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey)) {
                throw new RuntimeException("The configuration file must have full parameters, Please check the 'conf.properties'");
            }
        }

        endpoints = endpoint.split(";");
        final MinIONode[] nodes = new MinIONode[endpoints.length];
        for (int i = 0; i < endpoints.length; i++) {
            if (!endpoints[i].startsWith("http")) {
                endpoints[i] = "http://" + endpoints[i];
            }
            nodes[i] = new MinIONode(endpoints[i], accessKeyId, secretAccessKey);
        }

        try {
            minIOCluster = new MinIOCluster(nodes);
        } catch (MinioInitializeException e) {
            throw new RuntimeException(e);
        }
    }
}
