package com.joe.picBed.services.ImgStore;

import com.hujinwen.exceptions.minio.MinioPutObjectException;
import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.IOException;

public class OSSStoreServiceImplTest extends TestCase {

    public void testPutImg() throws IOException, MinioPutObjectException {
        final OSSStoreServiceImpl storeService = new OSSStoreServiceImpl();
        final FileInputStream fileInputStream = new FileInputStream("/Users/hujinwen/Downloads/ss.png");
        final String result = storeService.putImg("ss.png", fileInputStream);
        System.out.println();
    }
}