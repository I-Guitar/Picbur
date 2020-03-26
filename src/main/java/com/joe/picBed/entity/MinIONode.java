package com.joe.picBed.entity;

/**
 * Created by joe on 2020/3/26
 */
public class MinIONode {

    public final String endPoint;

    public final String accessKey;

    public final String secretAccesskey;

    public MinIONode(String endPoint, String accessKey, String secretAccesskey) {
        this.endPoint = endPoint;
        this.accessKey = accessKey;
        this.secretAccesskey = secretAccesskey;
    }
}
