package com.joe.picBed.utils;

import com.hujinwen.utils.FileUtils;

/**
 * Created by joe on 19-6-16
 */
public class Tools {

    public static String getImageExtName(String fileName) {
        String extraName = FileUtils.predictExtName(fileName);
        return extraName.length() > 0 ? extraName : "png";
    }

}
