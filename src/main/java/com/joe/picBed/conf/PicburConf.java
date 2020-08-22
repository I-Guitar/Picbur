package com.joe.picBed.conf;

import com.joe.picBed.conf.store.MinIOStoreConf;
import com.joe.picBed.conf.store.OSSStoreConf;

/**
 * Created by joe on 2020/8/3
 * <p>
 * Picbur 配置文件
 */
public class PicburConf {

    private MinIOStoreConf minIOStore;

    private OSSStoreConf ossStore;

    public MinIOStoreConf getMinIOStore() {
        return minIOStore;
    }

    public void setMinIOStore(MinIOStoreConf minIOStore) {
        this.minIOStore = minIOStore;
    }

    public OSSStoreConf getOssStore() {
        return ossStore;
    }

    public void setOssStore(OSSStoreConf ossStore) {
        this.ossStore = ossStore;
    }
}
