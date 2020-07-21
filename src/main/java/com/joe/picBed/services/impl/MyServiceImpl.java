package com.joe.picBed.services.impl;

import com.hujinwen.utils.EncryptUtils;
import com.hujinwen.utils.ObjectUtils;
import com.joe.picBed.entity.UploadRecordItem;
import com.joe.picBed.entity.exceptions.MinioPutObjectException;
import com.joe.picBed.services.ImageStoreService;
import com.joe.picBed.services.MyService;
import com.joe.picBed.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by joe on 2019/6/16
 */
@Service
public class MyServiceImpl implements MyService {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private final MessageFormat pathFormat = new MessageFormat("pic-bed/{0}/{1}.{2}");

    @Autowired
    private ImageStoreService imageStoreService;

    private LinkedList<UploadRecordItem> uploadRecord = new LinkedList<>();

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
    public UploadRecordItem upload(String filename, byte[] bytes) throws IOException, NoSuchAlgorithmException, MinioPutObjectException {
        try (
                InputStream inputStream = new ByteArrayInputStream(bytes)
        ) {
            final String url = imageStoreService.putImg(getFilePath(filename, bytes), inputStream);

            recordUpload(url);

            return uploadRecord.getLast();
        }
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

    private String getFilePath(String filename, byte[] bytes) throws NoSuchAlgorithmException {
        String folder = threadLocal.get().format(new Date());
        String fileName = EncryptUtils.md5(bytes);
        String extName = Tools.getImageExtName(filename);
        return pathFormat.format(new Object[]{folder, fileName, extName});
    }

}
