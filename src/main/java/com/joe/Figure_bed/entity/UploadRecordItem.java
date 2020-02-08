package com.joe.Figure_bed.entity;

import java.io.Serializable;

/**
 * Created by joe on 2020/2/5
 */
public class UploadRecordItem implements Serializable {

    /**
     * 图片url
     */
    private String url;

    /**
     * 上传时间
     */
    private long uploadTime;

    public UploadRecordItem(String url, long uploadTime) {
        this.url = url;
        this.uploadTime = uploadTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
