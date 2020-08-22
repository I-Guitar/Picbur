package com.joe.picBed.core;

import com.joe.picBed.conf.PicburConf;
import com.joe.picBed.services.ImgStore.ImgStoreService;
import com.joe.picBed.services.ImgStore.MinIOStoreServiceImpl;
import com.joe.picBed.services.ImgStore.OSSStoreServiceImpl;

/**
 * Created by hu-jinwen on 2020/8/22
 */
public class ImgStoreFactory {

    private static final MinIOStoreServiceImpl minIOStoreServer = new MinIOStoreServiceImpl();

    private static final OSSStoreServiceImpl ossStoreServer = new OSSStoreServiceImpl();

    public static ImgStoreService getStoreServer() {
        final PicburConf picburConf = PicburContext.getConf();

        if (!picburConf.getMinIOStore().isEmpty()) {
            return minIOStoreServer;
        }
        if (!picburConf.getOssStore().isEmpty()) {
            return ossStoreServer;
        }
        throw new RuntimeException("At least one OSS configuration or Minio configuration is required.");
    }

}
