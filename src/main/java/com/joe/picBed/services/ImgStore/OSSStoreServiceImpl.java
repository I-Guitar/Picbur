package com.joe.picBed.services.ImgStore;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.hujinwen.exceptions.minio.MinioPutObjectException;
import com.joe.picBed.conf.store.OSSStoreConf;
import com.joe.picBed.core.PicburContext;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Created by joe on 2020/8/3
 * <p>
 * OSS存储实现
 */
public class OSSStoreServiceImpl implements ImgStoreService {

    private OSS ossClient;

    private final OSSStoreConf config;

    private static final MessageFormat URL_FORMAT = new MessageFormat("https://{0}.{1}/{2}");

    public OSSStoreServiceImpl() {
        config = PicburContext.getConf().getOssStore();
    }

    @Override
    public String putImg(String objectName, InputStream inputStream) throws MinioPutObjectException, IOException {
        if (ossClient == null) {
            init();
        }
        final PutObjectResult putResult = ossClient.putObject(config.getBucketName(), objectName, inputStream);
        return URL_FORMAT.format(new Object[]{config.getBucketName(), config.getEndpoints(), objectName});
    }

    private void init() {
        ossClient = new OSSClientBuilder().build(config.getEndpoints(), config.getAccessKeyId(), config.getSecretAccessKey());
    }

    public void close() {
        ossClient.shutdown();
    }

}
