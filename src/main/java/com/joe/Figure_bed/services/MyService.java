package com.joe.Figure_bed.services;

import com.joe.Figure_bed.entity.UploadRecordItem;

import java.util.List;

/**
 * Create by joe on 2019/6/12
 */
public interface MyService {
    UploadRecordItem upload(String filename, byte[] bytes) throws Exception;

    List<UploadRecordItem> getUploadRecords();
}
