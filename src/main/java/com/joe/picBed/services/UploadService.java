package com.joe.picBed.services;

import com.joe.picBed.entity.UploadRecordItem;

import java.util.List;

/**
 * Create by joe on 2019/6/12
 */
public interface UploadService {
    UploadRecordItem upload(String filename, byte[] bytes) throws Exception;

    List<UploadRecordItem> getUploadRecords();
}
