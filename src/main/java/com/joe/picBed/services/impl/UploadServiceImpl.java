package com.joe.picBed.services.impl;

import com.joe.picBed.entity.UploadRecordItem;
import com.joe.picBed.services.AliOssService;
import com.joe.picBed.services.UploadService;
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
public class UploadServiceImpl implements UploadService {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private final MessageFormat pathFormat = new MessageFormat("Figure-bed/{0}/{1}.{2}");

    @Autowired
    private AliOssService ossService;

    private LinkedList<UploadRecordItem> uploadRecord = new LinkedList<>();

    private final String serializeFilePath = "./upload_record_cache";

    @PostConstruct
    public void init() throws IOException, ClassNotFoundException {
        if (new File(serializeFilePath).exists()) {
            uploadRecord = Tools.deserializeObj(serializeFilePath);
        }
    }

    /**
     * 上传图片
     */
    @Override
    public UploadRecordItem upload(String filename, byte[] bytes) throws IOException, NoSuchAlgorithmException {
        try (
                InputStream inputStream = new ByteArrayInputStream(bytes)
        ) {
            final String url = ossService.putObject("joe-data", getFilePath(filename, bytes), inputStream);

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

        Tools.serializeObj(uploadRecord, serializeFilePath);
    }

    private String getFilePath(String filename, byte[] bytes) throws NoSuchAlgorithmException {
        String folder = threadLocal.get().format(new Date());
        String fileName = Tools.md5(bytes);
        String extractName = Tools.getImageExtraName(filename);
        return pathFormat.format(new Object[]{folder, fileName, extractName});
    }

}
