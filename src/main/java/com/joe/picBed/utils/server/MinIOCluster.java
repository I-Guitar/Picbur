package com.joe.picBed.utils.server;

import com.joe.picBed.entity.MinIONode;
import com.joe.picBed.entity.exceptions.MinioInitializeException;
import com.joe.picBed.entity.exceptions.MinioPutObjectException;
import com.joe.picBed.utils.RandomUtils;
import io.minio.MinioClient;

import java.io.InputStream;

/**
 * Created by joe on 2020/3/20
 * <p>
 * minio 集群
 */
public class MinIOCluster {

    private final MinioClient[] cluster;

    public MinIOCluster(MinIONode... nodes) throws MinioInitializeException {
        cluster = new MinioClient[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            MinIONode node = nodes[i];
            try {
                cluster[i] = new MinioClient(node.endPoint, node.accessKey, node.secretAccesskey);
            } catch (Exception e) {
                throw new MinioInitializeException(e);
            }
        }
    }

    /**
     * 从集群中拿一个服务
     * <p>
     * TODO 应该做负载均衡，这里暂时直接随机一个
     */
    private MinioClient getClient() {
        return RandomUtils.randomChoice(cluster);
    }

    public void putObject(String bucketName, String objectName, InputStream inputStream, String contentType) throws MinioPutObjectException {
        try {
            getClient().putObject(bucketName, objectName, inputStream, null, null, null, contentType);
        } catch (Exception e) {
            throw new MinioPutObjectException(e);
        }
    }

    public void putImg(String bucketName, String objectName, InputStream inputStream) throws MinioPutObjectException {
        putObject(bucketName, objectName, inputStream, "image/png");
    }

}
