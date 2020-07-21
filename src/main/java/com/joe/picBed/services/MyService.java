package com.joe.picBed.services;

import com.joe.picBed.entity.UploadRecordItem;

import java.io.InputStream;
import java.util.List;

/**
 * Create by joe on 2019/6/12
 */
public interface MyService {
    UploadRecordItem upload(String filename, InputStream inputStream) throws Exception;

    List<UploadRecordItem> getUploadRecords();
}
