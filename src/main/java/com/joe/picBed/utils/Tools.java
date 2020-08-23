package com.joe.picBed.utils;

import com.hujinwen.utils.EncryptUtils;
import com.hujinwen.utils.FileUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hu-jinwen on 19-6-16
 */
public class Tools {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private static final MessageFormat pathFormat = new MessageFormat("pic-bed/{0}/{1}.{2}");


    public static String getImageExtName(String fileName) {
        String extraName = FileUtils.predictExtName(fileName);
        return extraName.length() > 0 ? extraName : "png";
    }

    public static String getFilePath(String filename) {
        String folder = threadLocal.get().format(new Date());
        String fileName = EncryptUtils.md5(filename);
        String extName = Tools.getImageExtName(filename);
        return pathFormat.format(new Object[]{folder, fileName, extName});
    }

}
