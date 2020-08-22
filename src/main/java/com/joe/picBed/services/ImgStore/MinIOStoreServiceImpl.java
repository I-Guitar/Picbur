package com.joe.picBed.services.ImgStore;

import com.hujinwen.client.minio.MinIOCluster;
import com.hujinwen.entity.minio.MinIONode;
import com.hujinwen.exceptions.minio.MinioInitializeException;
import com.hujinwen.exceptions.minio.MinioPutObjectException;
import com.hujinwen.utils.RandomUtils;
import com.joe.picBed.conf.store.MinIOStoreConf;
import com.joe.picBed.core.PicburContext;

import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Created by joe on 2020/3/20
 * <p>
 * MinIO存储实现
 */
public class MinIOStoreServiceImpl implements ImgStoreService {
    private MinIOCluster minIOCluster;

    private String[] endpoints;

    private final MinIOStoreConf config;

    private static final MessageFormat URL_FORMAT = new MessageFormat("{0}/{1}/{2}");

    public MinIOStoreServiceImpl() {
        config = PicburContext.getConf().getMinIOStore();
    }

    private void clientInit() {
        endpoints = config.getEndpoints().split(";");
        final MinIONode[] nodes = new MinIONode[endpoints.length];
        for (int i = 0; i < endpoints.length; i++) {
            if (!endpoints[i].startsWith("http")) {
                endpoints[i] = "http://" + endpoints[i];
            }
            nodes[i] = new MinIONode(endpoints[i], config.getAccessKeyId(), config.getSecretAccessKey());
        }

        try {
            minIOCluster = new MinIOCluster(nodes);
        } catch (MinioInitializeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String putImg(String objectName, InputStream inputStream) throws MinioPutObjectException {
        if (minIOCluster == null) {
            clientInit();
        }
        final String bucketName = config.getBucketName();
        minIOCluster.putImg(bucketName, objectName, inputStream);
        return URL_FORMAT.format(new Object[]{RandomUtils.randomChoice(endpoints), bucketName, objectName});
    }


}
