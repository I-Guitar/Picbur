package com.joe.picBed.services.ImgStore;


import com.hujinwen.exceptions.minio.MinioPutObjectException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joe on 2020/3/20
 * <p>
 * 图片存储服务类
 */
public interface ImgStoreService {

    /**
     * 存图片
     */
    String putImg(String objectName, InputStream inputStream) throws MinioPutObjectException, IOException;

}
