package com.joe.picBed.conf.store;

import com.hujinwen.utils.StringUtils;

/**
 * Created by joe on 2020/8/9
 */
public abstract class StoreConf {
    private String endpoints;

    private String bucketName;

    private String accessKeyId;

    private String secretAccessKey;

    public String getEndpoints() {
        return endpoints;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(endpoints) || StringUtils.isEmpty(bucketName)
                || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secretAccessKey);
    }

    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

}
