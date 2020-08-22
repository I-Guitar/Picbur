package com.joe.picBed.services;

import com.hujinwen.exceptions.minio.MinioPutObjectException;
import com.hujinwen.utils.ObjectUtils;
import com.joe.picBed.core.ImgStoreFactory;
import com.joe.picBed.entity.UploadRecordItem;
import com.joe.picBed.services.ImgStore.ImgStoreService;
import com.joe.picBed.utils.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by joe on 2019/6/16
 * <p>
 * 核心service实现
 */
@Service
public class PicburServiceImpl implements PicburService {

    /**
     * 图片上传记录
     */
    private LinkedList<UploadRecordItem> uploadRecord = new LinkedList<>();

    /**
     * 序列化存储位置
     */
    private final String serializeFilePath = "./upload_record_cache";

    @PostConstruct
    public void init() throws IOException, ClassNotFoundException {
        if (new File(serializeFilePath).exists()) {
            uploadRecord = ObjectUtils.deserializeObj(serializeFilePath);
        }
    }

    /**
     * 上传图片
     */
    @Override
    public UploadRecordItem upload(String filename, InputStream inputStream) throws IOException, MinioPutObjectException {
        final ImgStoreService storeServer = ImgStoreFactory.getStoreServer();
        final String url = storeServer.putImg(Tools.getFilePath(filename), inputStream);
        inputStream.close();
        recordUpload(url);
        return uploadRecord.getLast();
    }

    @Override
    public List<UploadRecordItem> getUploadRecords() {
        return uploadRecord;
    }

    /**
     * 记录上传信息
     *
     * @param url 上传成功后的url
     */
    private void recordUpload(String url) throws IOException {
        final long uploadTime = System.currentTimeMillis();
        final UploadRecordItem uploadRecordItem = new UploadRecordItem(url, uploadTime);
        uploadRecord.add(uploadRecordItem);

        if (uploadRecord.size() > 9) {
            uploadRecord.remove(0);
        }

        ObjectUtils.serializeObj(uploadRecord, serializeFilePath);
    }

}
