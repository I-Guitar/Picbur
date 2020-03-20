package com.joe.picBed.entity.enums;

/**
 * Created by joe on 2020/3/20
 * <p>
 * 文件 type 对应的 content type
 * 此处未全部列举 参考 https://app.yinxiang.com/shard/s20/nl/18962043/8caebb56-bf3b-4a80-b225-194a91954996
 */
public enum ContentType {
    DEFAULT("", "application/octet-stream"),
    AAB("001", "application/x-001"),
    CBC("323", "text/h323"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    GIF("gif", "image/gif");


    public final String fileType;

    public final String contentType;


    ContentType(String fileType, String contentType) {
        this.fileType = fileType;
        this.contentType = contentType;
    }


}
