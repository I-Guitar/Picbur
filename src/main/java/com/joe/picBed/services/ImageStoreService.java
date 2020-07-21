package com.joe.picBed.services;


import com.hujinwen.exception.minio.MinioPutObjectException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joe on 2020/3/20
 */
public interface ImageStoreService {

    /**
     * 存图片
     */
    String putImg(String objectName, InputStream inputStream) throws MinioPutObjectException, IOException;

}
