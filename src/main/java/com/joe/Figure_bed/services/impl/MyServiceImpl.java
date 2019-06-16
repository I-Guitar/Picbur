package com.joe.Figure_bed.services.impl;

import com.joe.Figure_bed.services.AliOssService;
import com.joe.Figure_bed.services.MyService;
import com.joe.Figure_bed.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joe on 2019/6/16
 */
@Service
public class MyServiceImpl implements MyService {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private final MessageFormat pathFormat = new MessageFormat("Figure-bed/{0}/{1}.{2}");

    @Autowired
    private AliOssService ossService;

    @Override
    public String upload(String filename, byte[] bytes) throws IOException, NoSuchAlgorithmException {
        try (
                InputStream inputStream = new ByteArrayInputStream(bytes)
        ) {
            String ossUrl = ossService.putObject("joe-data", getFilePath(filename, bytes), inputStream);
            return ossUrl + "<a>fanhui</a>";
        }
    }

    private String getFilePath(String filename, byte[] bytes) throws NoSuchAlgorithmException {
        String folder = threadLocal.get().format(new Date());
        String fileName = Tools.md5(bytes);
        String extractName = Tools.getImageExtraName(filename);
        return pathFormat.format(new Object[]{folder, fileName, extractName});
    }

}
