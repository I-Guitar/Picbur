package com.joe.picBed.services;

import com.joe.picBed.entity.UploadRecordItem;

import java.io.InputStream;
import java.util.List;

/**
 * Create by joe on 2019/6/12
 * <p>
 * 核心服务
 */
public interface PicburService {
    UploadRecordItem upload(String filename, InputStream inputStream) throws Exception;

    List<UploadRecordItem> getUploadRecords();
}
